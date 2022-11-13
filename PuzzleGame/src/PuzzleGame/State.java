/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PuzzleGame;

/**
 *
 * @author viet1
 */

import java.util.Arrays;
// Class dùng để mô tả một trạng thái
public class State implements Comparable<State>, Cloneable{ // Khai báo class thực hiện interface clone đối tượng, định nghĩa so sánh để làm việc với hàng đợi
    public int id; // thuộc tính này dùng để xác định đối tượng là cha
    public int subID; // thuộc tính này dùng để lưu lại id của đối tượng cha
    public Integer[] data; // Dùng để lưu lại dữ liệu của trạng thái
    public int h; // Hiện tại chưa xác định nên dùng hàm đánh giá nào
    public int g; // Chi phí đến thời điểm hiện tại (bước đi từ đầu đến giờ)
    public Operator op; // Các thao tác di chuyển của trạng thái

    public State() { // Nè là hàm tạo không đối á:::mấy nè làm cho vui hoi, khum dùng lém : >>>
    }

    public State(Integer[] data, int h, int g, Operator op) { // Nè là hàm khởi tạo có đối á :: làm cho vui hoi chứ cũng không có dùng
        this.data = data; // Gán data bằng data truyền vô
        this.h = h; // Nè là gán biểu thức đánh giá nè
        this.g = g; // Nè là gán chi phí tiến đến hiện tại
        this.op = op; // Nè là gán phương thức di chuyển (lên, xuống, trái, phải). Thức là trạng thái hiện tại là trạng thái đã được di chuyển từ ban đâu qua mô?
    }

    @Override
    // Hàm clone đối tượng. Why need clone? Vì cái java này với đối tượng và mảng thì nó sài tham chiều á
    // Nên khi gán đối tượng a = b, a thay đổi thì b cũng bị thay đổi theo. Nên là cần clone lại để tạo ra các trường hợp di chuyển từ trạng thái ban đầu
    public Object clone() throws CloneNotSupportedException{
        return super.clone(); // Cái supper này kiểu như thằng cha nó gọi đến clone á, lên mạng coi he
    }
    // Phương thức sao chép giá trị cho mảng, không qua tham chiếu
    // Vì khi mà clone cái đối tượng á, mảng nó bị tham chiếu chứ không được clone, nên phải xử lý riêng he
    public void copyArray(State s){
        this.data = Arrays.copyOfRange(s.data, 0, s.data.length); // nè là copy mảng s.data, từ vị trí 0 đến vị trí s.data.length he
    }
    // In ra mảng dữ liệu
    public void print(){
        try{ // Hàm này chỉ là in mảng ra màn hình, phục vụ debug với kiểm tra xe đúng hay chưa ó
            for(int i = 0; i < 3; ++ i){
                for(int j = 0; j < 3; ++ j)
                    System.out.print(this.data[i * 3 + j] + "  "); // Nè là toán học thoy, vẽ mảng hai chiều ra là thấy he
                System.out.println();
            }
        } catch (NullPointerException ex){ // Cho ném ngoại lệ vô vì nếu mà cái dữ liệu nó null á, thì mình không xài được, lỗi ngay
            System.out.println("Null");
        }
    }
    // Trả mảng về dạng chuỗi để so sánh, phục vụ cho việc lười sài for so sánh từng cái một á : >>
    public String key(){
        if(this.data == null) // Kiểm tra xem mảng hiện tại có null không, không có gì sao so sánh được
            return null; // Trả về null nha
        String s = ""; // Khởi tạo chuỗi s để lưu giá trị nè
        for(var i : this.data) // Nè là for lấy ra từng thằng trong mảng á, câu lệnh ngắn của for(int x,int i = 0; i < this.data.length; ++ i)
            s += i.toString(); // Chuyển số qua chuỗi mới cộng được nha
        return s; // Trả về chuỗi đã cộng xong he
    }

    @Override
    // Phương thức so sánh dùng để compare trong hàng đợi, sắp sếp tăng dần theo h + g
    public int compareTo(State o) { 
        return (this.h + this.g) - (o.h + o.g);
    }
    // Phương thức đánh giá để tìm kiếm đường đi
    public int countCellWrong(Integer[] s){ // Nè có nhiều hàm đánh giá lắm, chưa biết nên xài cái nào : >>
        int count = 0; // Nè là cái để đếm thoy
        for(int i = 0; i < 9; ++ i){
            if(this.data[i] != s[i]) // Truyền vô trạng thái đích nhưng khum có sài : >>> ☺
//                ++ count; // Nè là để đếm số ô sai so với trạng thái đích nè
//                count += Math.abs(this.data[i] - i); // Nè là đếm độ sai của vị trí ô hiện tại so với trạng thái đích
                // Ví dụ số 1 ở ô số 8, thì nó phải ở ô số 1 mới đúng, độ sai của nó là 8 - 1;
                count += Math.abs(data[i] - i) + Math.abs((data[i] - data[i] % 3) / 3 + data[i] % 3 - ((i - i % 3) / 3 + i % 3));
                // Hàm này là sử dụng độ sai và số bước di chuyển lý tưởng của mỗi ô để về vị trí ban đầu
        }
        return count; // Trả về giá trị đánh giá
    }

    @Override
    // Nè ghi để debug nhìn cho dễ
    public String toString(){
        return this.key() + ",h(" + this.h +"), g(" + this.g + ")";
    }
}

