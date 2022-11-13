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

public class Operator implements Cloneable { // Cho lớp thực hiện clone để sao chép một đối tượng, không tham chiếu thay đổi trực tiếp đối tượng
    // Class tạo ra để chỉ định phương thức di chuyển của đối trạng thái
    public int i; // Biến chỉ định cách di chuyển trên, dưới, trái, phải

    public Operator() { // Nè hàm khởi tạo không đối nè
    }

    public Operator(int i) { // Nè là phương thức khởi tạo có đối thoy
        this.i = i;
    }

    // Kiểm tra xem trạng thái có null không
    public boolean checkStateNull(State s){
        return s.data == null;
    }

    // Hàm đổi vị trí của trạng thái
    /* ví dụ */
    /*
    | 1 | 3 | 4 |
    | 2 | 0 | 5 |
    | 7 | 6 | 8 |
    Số 0 đại diện cho ô trống
    số 3 có thể đi xuống 0
    | 1 | 0 | 4 |
    | 2 | 3 | 5 |
    | 7 | 6 | 8 |
    2, 5 có thể sang 0
    6 có thể lên 0
    | 1 | 3 | 4 |
    | 2 | 6 | 5 |
    | 7 | 0 | 8 |
     */
    public State swap(State s, int locator, int i) throws CloneNotSupportedException {

        State sn = (State) s.clone(); // Phải clone lại trạng thái vì nếu không sẽ làm thay đổi trạng thái gốc, chỉ thay đổi được một lần
        sn.data = s.data;

        int pos = locator; // locator: vị trí hiện tại của ô trống (9)
        // up
        if(i == 0){ // nếu i = 0, tức là cho phép đi lên
            pos = locator - 3; // vị trí hiện tại của ô trống phải đi xuống dưới để phần tử khác đi lên
        }
        // down
        if(i == 1){ // tương tự như phần i bằng 0
            pos = locator + 3; // cái nè vẽ ra giấy cho dễ nhìn he
        }
        // right
        if(i == 2) // The same comment at i = 1
            pos = locator + 1; // The same comment at i = 1
        // left
        if(i == 3) // The same comment at i = 2
            pos = locator - 1; // Write in paper to make solution

        sn.data[locator] = s.data[pos]; // gán vị trí số 9 thành phần tử hoán đổi
        sn.data[pos] = 9; // gán vị trí phần tử hoán đổi thành ô trống
        return sn; // trả về trạng thái sau khi đã được hoán đổi
    }

    @Override // Ghi đè phương thức clone của giao diện Clonenable
    public Object clone() throws  CloneNotSupportedException{ // Ném ngoại lệ khi chưa khai báo thư viện nè
        return super.clone(); // Trả về thằng hiện tại khi đã được clone nha
    }
    // Hàm tìm vị trí của ô trống
    public int findPos(State s){
        int index = -1; // Khởi tạo vị trí hiện tại là -1
        for(var x : s.data){ // Nè same same foreach á, mà nó là foreach á : DD
            ++ index; // Tăng vị trí lên nè
            if(x == 9) // Nếu mà tìm được rồi
                break; // Thì thoát khỏi vòng lặp (Thật ra return luôn ở đây cũng được ☺
        }
        return index; // Trả về giá trị là vị trí của ô trống
    }
    // Hàm di chuyển lên
    public State up(State s) throws CloneNotSupportedException { // Cái swap là để phục vụ cho cái nè đó
        State sn = (State) s.clone(); // Clone lại cho chắc (hình như không cần lắm vì swap clone rồi)
        if(this.checkStateNull(sn)) // Kiểm tra xem trạng thái truyền vào có null hay không
            return null; // Có thì trả về null thuiii
        int x = this.findPos(sn); // Tìm vị trí của ô trống
        if(x < 3) return null;
        /*
        | 9 | 1 | 2 |
        | 3 | 4 | 5 |
        | 6 | 7 | 8 |
        nếu index của x < 3. Tức là vị trí sô 9 ở hàng đầu thì ô trống không thể đi lên được
         */
        return this.swap(sn, x, this.i); // Nếu mà ở hàng 2 và 3 thì vẫn đi lên được, nên swap thoi
    }
    // hàm di chuyển xuống
    public State down(State s) throws CloneNotSupportedException {
        State sn = (State) s.clone(); // Explain same comment at up
        if(this.checkStateNull(s)) // Explain same comment at up
            return null; // Explain same comment at up
        int x = this.findPos(s); // Explain same comment at up
        if(x > 5) return null; // Explain same comment at up
        return this.swap(sn, x, this.i); // Explain same comment at up
    }

    public State left(State s) throws CloneNotSupportedException { // Explain same comment at up
        State sn = (State) s.clone(); // Explain same comment at up
        if(this.checkStateNull(sn)) // Explain same comment at up
            return null; // Explain same comment at up
        int x = this.findPos(sn); // Explain same comment at up
        if(x % 3 == 0) return null; // Explain same comment at up
        return this.swap(sn, x, this.i); // Explain same comment at up
    }

    public State right(State s) throws CloneNotSupportedException { // Explain same comment at up
        State sn = (State) s.clone(); // Explain same comment at up
        if(this.checkStateNull(sn)) // Explain same comment at up
            return null; // Explain same comment at up
        int x = this.findPos(sn); // Explain same comment at up
        if(x % 3 == 2) return null; // Explain same comment at up
        return this.swap(sn, x, this.i); // Explain same comment at up
    }

    // Hàm thực hiện di chuyển của trạng thái s
    // Nè là tích hợp của mấy cái trên á, sau qua main gọi thì nó nhanh hơn, đỡ phải if else nhiều lần
    public State move(State s) throws CloneNotSupportedException { // Explain same comment at up
        State sn = (State) s.clone(); // Explain same comment at up
        if(this.i == 0) // Explain same comment at up
            return this.up(sn); // Explain same comment at up
        if(this.i == 1) // Explain same comment at up
            return this.down(sn); // Explain same comment at up
        if(this.i == 2) // Explain same comment at up
            return this.right(sn); // Explain same comment at up
        if(this.i == 3) // Explain same comment at up
            return this.left(sn); // Explain same comment at up
        return null; // Nè là để return nếu không di chuyển được á
    }
}

