/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PuzzleGame;

/**
 *
 * @author viet1
 */

import java.io.*; // Nè là import tất cả thư viện io nè
import java.util.*; // Nè là import tất cả thư viện của package util. Lười viết á

public class Main { 
    static int ID = -1; // ID này dùng để lưu lại vị trí của nút cha trong mảng, phục vụ việc truy vết nhe
    public static int saw = 0; // 
    static private final Integer[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // Đây là khai báo trạng thái đích nhá
    /*
    Bài toán puzzle 8 số
    Trạng thái đích
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
     */
    
    /***************************************************************************/
    //======================= Hàm chạy để giải bài toán =======================//
    /***************************************************************************/
    static public void solution(Integer[] in) throws CloneNotSupportedException{ 
        State start = new State(); // Khởi tạo trạng thái ban đầu
        start.data = Arrays.copyOfRange(in, 0, in.length); // Copy dữ liệu ban đầu cho trạng thái
        Run(start); // Bắt đầu chạy
    }
    /***************************************************************************/
    
    
    /***************************************************************************/
    //========================== Hàm main để test =============================//
    /***************************************************************************/
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        Integer[] in = {9, 6, 2, 1, 5, 3, 4, 7, 8}; // Tạo trạng thái đầu vào để test
        State start = new State(); // Khởi tạo trạng thái ban đầu
        start.data = Arrays.copyOfRange(in, 0, in.length); // Copy dữ liệu ban đầu cho trạng thái
        Run(start); // bắt đầu chạy
    }
    /***************************************************************************/

    
    /***************************************************************************/
    //================= Tạo các hàng đợi và danh sách để lưu trữ ==============//
    /***************************************************************************/
    static private Queue<State> Open = new PriorityQueue<>(); // tạo hàng đợi danh sách mở
    static private Queue<State> Closed = new PriorityQueue<>(); // tạo hàng đợi danh sách đóng
    static private List<State> address = new ArrayList<State>(); // Tạo list để lưu các trạng thái được lấy ra để duyệt, phục vụ truy vết
    static public List<State> temp = new ArrayList<State>(); // Tạo list để lưu lại trạng thái, đường đi sau khi đã truy vết
    /***************************************************************************/

    
    /***************************************************************************/
    //======= Hàm kiểm tra xem trạng thái tồn tại trong hàng đợi hay chưa =====//
    /***************************************************************************/
    static private boolean checkInList(Queue<State> state, State s){ // Kiểm tra xem trạng thái đã tồn tại trong hàng đợi chưa
        Iterator iterator = state.iterator(); // Iterator là đối tượng để thực hiện vòng lặp, hiểu nó như foreach
        while(iterator.hasNext()){ // hasNext này hiểu là vẫn còn phần tử ở sau á
            State x = (State) iterator.next(); // Nè là lấy ra trạng thái kế tiếp trong hàng đợi
            if(x.key().equals(s.key())) // Kiểm tra xem tồn tại hay chưa trạng thái đang xét
                return true; // Trả về true nếu đã tồn tại
        }
        return false; // Trả về false nếu không tồn tại (chưa tồn tại)
    }
    /***************************************************************************/
    
    
    /***************************************************************************/
    //====================== Hàm dùng để cập nhật lại Open ====================//
    /***************************************************************************/
    static void update(Queue<State> open, Queue<State> closed, State s, boolean cho, boolean chcl){
        // Hàm này tạo ra là để kiểm tra xem trong close trạng thái đang xét có giá thấp hơn close hay không
        // Nếu có thì cập nhật lại trạng thái vào open
        // Còn không thì bỏ qua
        if(chcl){ // Nếu trạng thái đang xét có trong close
            boolean flag = false; // Tạo cờ kiểm tra là false (chưa có)
            Iterator iterator = closed.iterator(); // Tạo đối tượng lặp duyệt hàng đợi
            while(iterator.hasNext()){ // Khi mà vẫn chưa ở cuối hàng đợi
                State x = (State) iterator.next(); // Lấy ra phần tử tiếp theo để xét
                if(x.key().equals(s.key()) && x.g > s.g){ // Kiểm tra xem trạng thái đang xét có giá thấp hơn trạng thái đã xét hay không?
                    flag = true; // Gán cờ là true(có thỏa mãn là giá nhỏ hơn trạng thái đã xét)
                    break; // Thoát khỏi vòng lặp
                }
            }
            
            if(flag){ // Nếu như trạng thái đang xét thỏa mãn có trong close và giá nhỏ hơn
                iterator = open.iterator(); // Tạo đối tượng lặp để duyệt hàng đợi mở
                int i = 0; // Nè là để check index
                while(iterator.hasNext()){ // Khi mà vẫn chưa ở cuối hàng đợi
                    State x = (State) iterator.next(); // Lấy ra phần tử tiếp theo của hàng đợi mở
                    if(x.key().equals(s.key()) && x.g > s.g){ // Kiểm tra xem trạng thái đang xét có giá thấp hơn trạng thái đã xét hay không?
                        open.remove(i); // Thực hiện loại bỏ trạng thái có giá lớn hơn
                        open.add(s); // Thêm trạng thái mới có giá nhỏ hơn vào hàng đợi
                        return; // Thoát khỏi chương trình
                    }
                    ++ i; // Tăng chỉ số lên 1
                }
                open.add(s); // Nếu như chưa có trạng thái trong open thì thêm vào open
            }
        } else if(cho){ // Nếu như trạng thái đang xét có trong open
            Iterator iterator = open.iterator(); // The same comment in line 93
            int i = 0; // The same comment in line 94
            while(iterator.hasNext()){ // The same comment in line 95
                State x = (State) iterator.next(); // The same comment in line 96
                if(x.key().equals(s.key()) && x.g > s.g){ // The same comment in line 97
                    open.remove(i); // The same comment in line 98
                    open.add(s); // The same comment in line 99
                    return; // The same comment in line 100
                }
                ++ i; // The same comment in line 102
            }
        }
    }
    /***************************************************************************/
    
    
    /***************************************************************************/
    //=============== Hàm này dùng để thực hiện chạy chương trình =============//
    /***************************************************************************/
    static private void Run(State s) throws CloneNotSupportedException {
        ID = -1; // Khởi gán lại ID bằng -1 để đếm lại từ đầu khi chạy chương trình mới
        temp.clear(); // Xóa hết dữ liệu trong tệp truy vết
        Open.clear(); // Xóa hết dữ liệu, làm trống hàng đợi open 
        Closed.clear(); // Xóa hết dữ liệu, làm trống hàng đợi close
        address.clear(); // Hóa hết dữ liệu, làm trống danh sách truy vết
        saw = 0; // Khởi gán lại số bước thực hiện bằng 0
        //=====================================================================//
        s.subID = -1; // Gán id của cha ban đầu bằng -1 (tức là trạng thái ban đầu không có cha)
        s.g = 0; // Chi phí ban đầu chưa đi nên gán bằng 0
        s.h = s.countCellWrong(goal); // Đếm định giá sai khác bằng hàm đã định nghĩa bên State
        Open.add(s); // Thêm trạng thái đầu vào danh sách mở
        while (true){ // Thực hiện vòng lặp khi mở vẫn khác rỗng
            if(Open.isEmpty() == true){ // Nếu như hàng đợi mở không có trạng thái
                System.out.println("Fail!"); // In ra lỗi
                return; // Kết thúc chương trình
            }
            State O = Open.poll(); // Lấy ra phần tử đầu tiên của hàng đợi
            Closed.add(O); // Thêm phần tử này vào danh sách đã duyệt
            O.id = ++ ID; // Gán id của trạng thái đang duyệt bằng ID tự sinh
            address.add(O); // Thêm trạng thái đang xét vào trong danh sách đã duyệt để thực hiện truy vết
            if(O.countCellWrong(goal) == 0){ // Kiểm tra, nếu trạng thái đang xét là trạng thái đích
                System.out.println("Success..."); // In ra màn hình "thành công"
                saw = Closed.size(); // Lấy ra số đỉnh mà thuật toán đã xét qua
                Path(address.get(address.size() - 1)); // Thực hiện truy vết từ trạng thái con cuối cùng về trạng thái ban đầu
                break; // Thoát khỏi vòng lặp
            }
            //=================================================================//
            for(int x = 0; x < 4; ++ x){ // dùng for thực hiện thao tác up, down, left, right để sinh trạng thái
                State spam = (State) O.clone(); // clone lại trạng thái đang xét, để mỗi lần di chuyển trạng thái vẫn giữ được nguyên dạng ban đầu để di chuyển
                spam.copyArray(O); // copy mảng để xử lý dữ liệu, tránh bị thay đổi dữ liệu của mảng gốc
                Operator op = new Operator(x); // tạo thao tác di chuyển 
                State child = op.move(spam); // gán trạng thái con bằng trạng thái cha di chuyển 1 lần
                if(child == null) // Kiểm tra nếu trạng thái cha không thể di chuyển được
                    continue; // Tiếp tục vòng lặp tiếp theo
                boolean check1 = checkInList(Open, child); // kiểm tra xem trạng thái đang xét có trong open hay ko
                boolean check2 = checkInList(Closed, child); // Kiểm tra xem trạng thái đang xét được duyệt chưa
                if(!check1 && !check2){ // Nếu chưa được duyệt và chưa có trong open
                    child.subID = O.id; // Lưu lại địa chỉ của trạng thái cha cho trạng thái con để phục vụ truy vết
                    child.g = O.g + 1; // Đếm số bước đi hiện tại
                    child.h = child.countCellWrong(goal); // Tính định giá trạng thái con bằng hàm định giá đã định nghĩa bên class State
                    Open.add(child); // Thêm trạng thái con vào danh sách chờ duyệt
                } else { // Nếu trạng thái con đang xét đã có trong open hoặc close hoặc open và close
                    child.subID = O.id; // Lưu lại địa chỉ trạng thái cha cho trạng thái con
                    child.g = O.g + 1; // Tính số bước đi đến thời điểm hiện tại
                    child.h = child.countCellWrong(goal); // Tính định giá trạng thái con bằng hàm định giá đã định nghĩa bên class State
                    update(Open, Closed, s, check1, check2); // Cập nhật lại hàng đợi mở
                }
            }
        }
    }
    /***************************************************************************/
    
    
    /***************************************************************************/
    //=================== Hàm dùng để thực hiên việc truy vết =================//
    /***************************************************************************/
    static private void Path(State O) {
        if(O.subID == -1){ // Kiểm tra nếu như là trạng thái đầu vào (ban đầu)
            temp.add(0,O); // Thêm trạng thái đầu vào tệp đã truy vết
            O.print(); // In trạng thái ra màn hình
            System.out.println("==="); // Comment cho dễ nhìn
            return; // Thoát khỏi chương trình
        }
        O.print(); // In ra trạng thái đang truy vết
        System.out.println("==="); // Comment cho dễ nhìn
        temp.add(0, O); // Thêm trạng thái đang truy vết vào tệp truy vết
        Path(address.get(O.subID)); // Thực hiện đệ quy để truy vết ngược
    }
    /***************************************************************************/
}