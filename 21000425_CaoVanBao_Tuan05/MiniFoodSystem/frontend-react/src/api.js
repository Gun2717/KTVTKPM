import axios from "axios";

const USER_BASE = import.meta.env.VITE_USER_SERVICE_URL || "http://localhost:8081/users";
const FOOD_BASE = import.meta.env.VITE_FOOD_SERVICE_URL || "http://localhost:8082/foods";
const ORDER_BASE = import.meta.env.VITE_ORDER_SERVICE_URL || "http://localhost:8083/orders";
const PAYMENT_BASE = import.meta.env.VITE_PAYMENT_SERVICE_URL || "http://localhost:8084/payments";

export const userApi = {
  register: (payload) => axios.post(`${USER_BASE}/register`, payload),
  login: (payload) => axios.post(`${USER_BASE}/login`, payload),
  getUsers: () => axios.get(USER_BASE)
};

export const foodApi = {
  getFoods: () => axios.get(FOOD_BASE)
};

export const orderApi = {
  createOrder: (payload) => axios.post(ORDER_BASE, payload),
  getOrders: () => axios.get(ORDER_BASE)
};

export const paymentApi = {
  pay: (payload) => axios.post(PAYMENT_BASE, payload)
};
