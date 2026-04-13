import { useNavigate } from "react-router-dom";
import { useAppStore } from "../store";

export default function CartPage() {
  const navigate = useNavigate();
  const { cart, cartTotal, updateQuantity, createOrderFromCart } = useAppStore();
  const empty = cart.length === 0;

  const createOrder = async () => {
    if (empty) return;
    await createOrderFromCart();
    navigate("/orders");
  };

  return (
    <section className="card">
      <h2>Giỏ hàng</h2>
      {empty ? (
        <p>Giỏ hàng đang trống.</p>
      ) : (
        <div className="table">
          {cart.map((item) => (
            <div key={item.foodId} className="table-row">
              <span>{item.name}</span>
              <input
                type="number"
                min="0"
                value={item.quantity}
                onChange={(e) => updateQuantity(item.foodId, Number(e.target.value))}
              />
              <span>{(item.price * item.quantity).toLocaleString()} VND</span>
            </div>
          ))}
        </div>
      )}
      <div className="row-between">
        <strong>Tổng tiền: {cartTotal.toLocaleString()} VND</strong>
        <button disabled={empty} onClick={createOrder}>
          Tạo đơn hàng
        </button>
      </div>
    </section>
  );
}
