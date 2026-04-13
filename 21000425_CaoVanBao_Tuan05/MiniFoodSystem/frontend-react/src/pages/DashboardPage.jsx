import { Navigate } from "react-router-dom";
import { useAppStore } from "../store";

export default function DashboardPage() {
  const { currentUser } = useAppStore();
  return <Navigate to={currentUser ? "/foods" : "/login"} replace />;
}
