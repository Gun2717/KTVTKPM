# BaiTap02

Mô phỏng tính thuế cho sản phẩm trong hệ thống bán hàng với 3 Design Pattern:

- **State**: Trạng thái áp thuế theo loại sản phẩm (miễn thuế / chuẩn / tiêu thụ / xa xỉ).
- **Strategy**: Thuật toán tính từng loại thuế (VAT, tiêu thụ, xa xỉ).
- **Decorator**: Ghép nhiều loại thuế lại với nhau (VD: VAT + thuế tiêu thụ) mà không sửa code lõi.

## Chạy chương trình

Trong thư mục `BaiTap02`:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main"
```

