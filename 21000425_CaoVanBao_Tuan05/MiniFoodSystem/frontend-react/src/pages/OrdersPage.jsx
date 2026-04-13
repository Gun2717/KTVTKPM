import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { orderApi } from "../api";
import { useAppStore } from "../store";

export default function OrdersPage() {
  const navigate = useNavigate();
  const { selectedOrder, selectOrder } = useAppStore();
  const [orders, setOrders] = useState([]);

  const loadOrders = async () => {
    const { data } = await orderApi.getOrders();
    const list = Array.isArray(data) ? data : [data];
    setOrders(list);
  };

  useEffect(() => {
    loadOrders();
  }, []);

  return (
    <section className="card">
      <div className="row-between">
        <h2>Lịch sử đơn hàng</h2>
        <button onClick={loadOrders}>Làm mới</button>
      </div>
      {orders.length === 0 ? (
        <p>Chưa có đơn hàng.</p>
      ) : (
        <div className="table">
          {orders.map((order) => (
            <div key={order.id} className="table-row">
              <span>#{order.id}</span>
              <span>{order.total.toLocaleString()} VND</span>
              <span>{order.status}</span>
              <button
                onClick={() => {
                  selectOrder(order);
                  navigate("/payment");
                }}
              >
                Thanh toán
              </button>
            </div>
          ))}
        </div>
      )}
      {selectedOrder && <p>Đang chọn đơn #{selectedOrder.id}</p>}
    </section>
  );
}
