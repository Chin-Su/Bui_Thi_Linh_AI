/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PuzzleGame;

/**
 *
 * @author viet1
 */

import java.io.*;
import java.util.*;

public class Main {
    static int ID = -1;
    public static int saw = 0;
    static private Integer[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    /*
    Bài toán puzzle 8 số
    Trạng thái đích
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 0 |
     */
    
    static public void solution(Integer[] in) throws CloneNotSupportedException{
        State start = new State(); // Khởi tạo trạng thái ban đầu
        start.data = Arrays.copyOfRange(in, 0, in.length); // Copy dữ liệu ban đầu cho trạng thái
        Run(start); // bắt đầu chạy
    }
    
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        Integer[] in = {9, 6, 2, 1, 5, 3, 4, 7, 8}; // tạo trạng thái đầu vào
        State start = new State(); // Khởi tạo trạng thái ban đầu
        start.data = Arrays.copyOfRange(in, 0, in.length); // Copy dữ liệu ban đầu cho trạng thái
        Run(start); // bắt đầu chạy
    }

    /*********************************************************************/
    static private Queue<State> Open = new PriorityQueue<>(); // tạo hàng đợi danh sách mở
    static private Queue<State> Closed = new PriorityQueue<>(); // tạo hàng đợi danh sách đóng
    static private List<State> address = new ArrayList<State>();
    /*********************************************************************/

    static private boolean checkInList(Queue<State> state, State s){ // Kiểm tra xem trạng thái đã tồn tại trong hàng đợi chưa
        Iterator iterator = state.iterator();
        while(iterator.hasNext()){
            State x = (State) iterator.next();
            if(x.key().equals(s.key()))
                return true;
        }

        return false;
    }

    static private void Run(State s) throws CloneNotSupportedException {
//        Queue<State> Open = new PriorityQueue<>(); // tạo hàng đợi danh sách mở
//        Queue<State> Closed = new PriorityQueue<>(); // tạo hàng đợi danh sách đóng
//        s.id = -1;
        ID = -1;
        temp.clear();
        Open.clear();
        Closed.clear();
        address.clear();
        saw = 0;
        /******************************************/
        s.subID = -1;
        s.g = 0; // chi phí đã đi = 0
        s.h = s.countCellWrong(goal); // số ô sai
//        s.parent = null; // cha ban đầu rỗng
        Open.add(s); // Thêm trạng thái đầu vào danh sách mở
//        address.add(s);
//        int dl = 0;
        while (true){ // thực hiện khi mở vẫn khác rỗng
//            ++ dl;
            if(Open.isEmpty() == true){ // khác rỗng return sai
                System.out.println("Fail!");
                return;
            }
            State O = Open.poll(); // Lấy ra phần tử đầu tiên của hàng đợi
            Closed.add(O); // Thêm phần tử này vào danh sách đã duyệt
            O.id = ++ ID;
            address.add(O);
            if(O.countCellWrong(goal) == 0){ // nếu như trạng thái là trang thái đích thì return
                System.out.println("Success...");
                saw = Closed.size();
//                for(var i : address){
//                    System.out.println("====");
//                    i.print();
//                    System.out.println(i.id);
//                    System.out.println(i.subID);
//                }
                Path(address.get(address.size() - 1));
//                address.get(address.size() - 1).print();
//                Path(O); // thực hiện lần vết
//                System.out.println(address.size());
                break;
            }

            for(int x = 0; x < 4; ++ x){ // dùng for thực hiện thao tác up, down, left, right để sinh trạng thái
                State spam = (State) O.clone(); // clone :
                spam.copyArray(O); // copy mảng
                Operator op = new Operator(x); // tạo thao tác
                State child = op.move(spam); // gán trạng thái con bằng trạng thái cha di chuyển 1 lần
                if(child == null)
                    continue;
                boolean check1 = checkInList(Open, child); // kiểm tra xem có trong open hay ko
                boolean check2 = checkInList(Closed, child); // Kiểm tra xem trạng thái được duyệt chưa
                if(!check1 && !check2){ // Nếu chưa được duyệt và chưa có trong open
//                    child.parent = (State) O.clone(); // clone
//                    child.parent.copyArray(O); // gán cha của con bằng cha
//                    child.id = ++ ID;
//                    child.id = address.size();
                    child.subID = O.id;
//                    child.op = (Operator) op.clone();
                    child.g = O.g + 1; // đếm chi phí
                    child.h = child.countCellWrong(goal); // đếm ô sai
                    Open.add(child); // thêm con vào danh sách chờ duyệt
//                    System.out.println("***************************");
//                    State r = Open.peek();
//                    address.add(r);

//                    r.print();
//                    System.out.println("---------------------------------");
//                    System.out.println(r.id);
//                    System.out.println(r.subID);



//                    try{
//                        r.parent.print();
//                    } catch (NullPointerException ex){
//                        System.out.println("Null");
//                    }
//                    System.out.println("=======================");
//                    child.parent.print();
//                    child.print();
//                    System.out.println("h = " + child.h + ", g = " + child.g + "");
//                    System.out.println("==========================");
//                    System.out.println(Open);
//                    System.out.println(Closed);
                }
            }
//            if(dl == 10)
//                break;
        }
    }

//    static void Path(State O){
//        if(O.parent == null){
//            Path(O.parent);
//        }
//        O.print();
//        System.out.println();
//    }
    
    static public List<State> temp = new ArrayList<State>();

    static private void Path(State O) {
        if(O.subID == -1){
            temp.add(0,O);
            O.print();
            System.out.println("===");
            return;
        }
        O.print();
        System.out.println("===");
        temp.add(0, O);
        Path(address.get(O.subID));
    }
}