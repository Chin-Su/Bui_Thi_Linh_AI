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

public class Operator implements Cloneable {
    // Class tạo ra để chỉ định phương thức di chuyển của đối trạng thái
    public int i; // Biến chỉ định cách di chuyển trên, dưới, trái, phải

    public Operator() {
    }

    public Operator(int i) {
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

        State sn = (State) s.clone();
        sn.data = s.data;

        int pos = locator; // locator: vị trí hiện tại của ô trống (0)
        // up
        if(i == 0){ // nếu i = 0, tức là cho phép đi lên
            pos = locator - 3; // vị trí hiện tại của ô trống phải đi xuống dưới để phần tử khác đi lên
        }
        // down
        if(i == 1){ // tương tự
            pos = locator + 3;
        }
        // right
        if(i == 2)
            pos = locator + 1;
        // left
        if(i == 3)
            pos = locator - 1;

        sn.data[locator] = s.data[pos]; // gán vị trí số 0 thành phần tử hoán đổi
        sn.data[pos] = 9; // gán vị trí phần tử hoán đổi thành ô trống
        return sn;
    }

    @Override
    public Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }
    // Hàm tìm vị trí của ô trống
    public int findPos(State s){
        int index = -1;
        for(var x : s.data){
            ++ index;
            if(x == 9)
                break;
        }
        return index;
    }
    // Hàm di chuyển lên
    public State up(State s) throws CloneNotSupportedException {
        State sn = (State) s.clone();
        if(this.checkStateNull(sn))
            return null;
        int x = this.findPos(sn);
        if(x < 3) return null;
        /*
        | 0 | 1 | 2 |
        | 3 | 4 | 5 |
        | 6 | 7 | 8 |
        nếu index của x > 5. tức là vị trí sô 0 ở hàng đầu thì ô trống không thể đi lên được
         */
        return this.swap(sn, x, this.i);
    }
    // hàm di chuyển xuống
    public State down(State s) throws CloneNotSupportedException {
        State sn = (State) s.clone();
        if(this.checkStateNull(s))
            return null;
        int x = this.findPos(s);
        if(x > 5) return null;
        return this.swap(sn, x, this.i);
    }

    public State left(State s) throws CloneNotSupportedException {
        State sn = (State) s.clone();
        if(this.checkStateNull(sn))
            return null;
        int x = this.findPos(sn);
        if(x % 3 == 0) return null;
        return this.swap(sn, x, this.i);
    }

    public State right(State s) throws CloneNotSupportedException {
        State sn = (State) s.clone();
        if(this.checkStateNull(sn))
            return null;
        int x = this.findPos(sn);
        if(x % 3 == 2) return null;
        return this.swap(sn, x, this.i);
    }

    // Hàm thực hiện di chuyển của trạng thái s
    public State move(State s) throws CloneNotSupportedException {
        State sn = (State) s.clone();
        if(this.i == 0)
            return this.up(sn);
        if(this.i == 1)
            return this.down(sn);
        if(this.i == 2)
            return this.right(sn);
        if(this.i == 3)
            return this.left(sn);
        return null;
    }
}

