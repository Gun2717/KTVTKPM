import { Navigate } from "react-router-dom";
import { useAppStore } from "../store";

export default function ProtectedRoute({ children }) {
  const { currentUser } = useAppStore();
  if (!currentUser) {
    return <Navigate to="/login" replace />;
  }
  return children;
}
