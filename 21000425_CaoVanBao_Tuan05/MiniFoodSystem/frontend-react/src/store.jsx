import { createContext, useContext, useMemo, useState } from "react";
import { orderApi } from "./api";

const AppStoreContext = createContext(null);

const readJson = (key, fallback) => {
  try {
    const raw = localStorage.getItem(key);
    return raw ? JSON.parse(raw) : fallback;
  } catch {
    return fallback;
  }
};

export function AppStoreProvider({ children }) {
  const [currentUser, setCurrentUser] = useState(() => readJson("mfs_user", null));
  const [cart, setCart] = useState(() => readJson("mfs_cart", []));
  const [selectedOrder, setSelectedOrder] = useState(() => readJson("mfs_selected_order", null));

  const persist = (key, value) => localStorage.setItem(key, JSON.stringify(value));

  const login = (user) => {
    setCurrentUser(user);
    persist("mfs_user", user);
  };

  const logout = () => {
    setCurrentUser(null);
    setCart([]);
    setSelectedOrder(null);
    localStorage.removeItem("mfs_user");
    localStorage.removeItem("mfs_cart");
    localStorage.removeItem("mfs_selected_order");
  };

  const addToCart = (food) => {
    setCart((prev) => {
      const existed = prev.find((item) => item.foodId === food.id);
      const next = existed
        ? prev.map((item) =>
            item.foodId === food.id ? { ...item, quantity: item.quantity + 1 } : item
          )
        : [...prev, { foodId: food.id, name: food.name, price: food.price, quantity: 1 }];
      persist("mfs_cart", next);
      return next;
    });
  };

  const updateQuantity = (foodId, quantity) => {
    setCart((prev) => {
      const next = prev
        .map((item) => (item.foodId === foodId ? { ...item, quantity } : item))
        .filter((item) => item.quantity > 0);
      persist("mfs_cart", next);
      return next;
    });
  };

  const clearCart = () => {
    setCart([]);
    persist("mfs_cart", []);
  };

  const cartTotal = useMemo(
    () => cart.reduce((sum, item) => sum + item.price * item.quantity, 0),
    [cart]
  );

  const createOrderFromCart = async () => {
    const payload = {
      userId: currentUser.id,
      items: cart.map((item) => ({ foodId: item.foodId, quantity: item.quantity }))
    };
    const { data } = await orderApi.createOrder(payload);
    setSelectedOrder(data);
    persist("mfs_selected_order", data);
    return data;
  };

  const selectOrder = (order) => {
    setSelectedOrder(order);
    persist("mfs_selected_order", order);
  };

  return (
    <AppStoreContext.Provider
      value={{
        currentUser,
        cart,
        cartTotal,
        selectedOrder,
        login,
        logout,
        addToCart,
        updateQuantity,
        clearCart,
        createOrderFromCart,
        selectOrder
      }}
    >
      {children}
    </AppStoreContext.Provider>
  );
}

export function useAppStore() {
  const value = useContext(AppStoreContext);
  if (!value) {
    throw new Error("useAppStore must be used inside AppStoreProvider");
  }
  return value;
}
