import { useEffect, useState } from "react";
import { foodApi } from "../api";
import { useAppStore } from "../store";

export default function FoodsPage() {
  const { addToCart } = useAppStore();
  const [foods, setFoods] = useState([]);
  const [message, setMessage] = useState("");

  const loadFoods = async () => {
    try {
      const { data } = await foodApi.getFoods();
      setFoods(data);
    } catch {
      setMessage("Không tải được danh sách món ăn");
    }
  };

  useEffect(() => {
    loadFoods();
  }, []);

  return (
    <section className="card">
      <div className="row-between">
        <h2>Danh sách món ăn</h2>
        <button onClick={loadFoods}>Làm mới</button>
      </div>
      <div className="grid">
        {foods.map((food) => (
          <div key={food.id} className="item">
            <h3>{food.name}</h3>
            <p>{food.price.toLocaleString()} VND</p>
            <button onClick={() => addToCart(food)}>Thêm vào giỏ</button>
          </div>
        ))}
      </div>
      {message && <p className="message">{message}</p>}
    </section>
  );
}
