import { useState } from "react";
import { paymentApi } from "../api";
import { useAppStore } from "../store";

const methods = [
  { value: "COD", label: "COD (Thanh toán khi nhận hàng)" },
  { value: "Banking", label: "Chuyển khoản ngân hàng" }
];

export default function PaymentPage() {
  const { selectedOrder, clearCart } = useAppStore();
  const [method, setMethod] = useState("COD");
  const [message, setMessage] = useState("");

  const pay = async () => {
    if (!selectedOrder) {
      setMessage("Vui lòng tạo hoặc chọn đơn hàng trước.");
      return;
    }
    try {
      const { data } = await paymentApi.pay({ orderId: selectedOrder.id, method });
      setMessage(typeof data === "string" ? data : "Thanh toán thành công");
      clearCart();
    } catch (error) {
      setMessage(error.response?.data?.message || "Thanh toán thất bại");
    }
  };

  return (
    <section className="card">
      <h2>Thanh toán</h2>
      <p>Đơn đang chọn: {selectedOrder ? `#${selectedOrder.id}` : "Chưa có đơn nào"}</p>
      <div className="row">
        <select value={method} onChange={(e) => setMethod(e.target.value)}>
          {methods.map((m) => (
            <option key={m.value} value={m.value}>
              {m.label}
            </option>
          ))}
        </select>
        <button onClick={pay}>Xác nhận thanh toán</button>
      </div>
      {message && <p className="message">{message}</p>}
    </section>
  );
}
