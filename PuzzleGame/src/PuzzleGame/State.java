/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PuzzleGame;

/**
 *
 * @author viet1
 */

import java.io.IOException;
import java.util.Arrays;
// Class dùng để mô tả một trạng thái
public class State implements Comparable<State>, Cloneable{
    public int id;
    public int subID;
    public Integer[] data; // Dùng để lưu lại dữ liệu của trạng thái
//    public State parent; // Lưu lại nút cha của trạng thái
    public int h; // Số ô sai
    public int g; // Chi phí đến thời điểm hiện tại
    public Operator op; // Các thao tác di chuyển của trạng thái

    public State() {
    }

    public State(Integer[] data, int h, int g, Operator op) {
        this.data = data;
//        this.parent = parent;
        this.h = h;
        this.g = g;
        this.op = op;
    }

    @Override
    // Hàm clone đối tượng
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    // Phương thức sao chép giá trị cho dữ liệu, không qua tham chiếu
    public void copyArray(State s){
        this.data = Arrays.copyOfRange(s.data, 0, s.data.length);
    }
    // In ra mảng dữ liệu
    public void print(){
        try{
            for(int i = 0; i < 3; ++ i){
                for(int j = 0; j < 3; ++ j)
                    System.out.print(this.data[i * 3 + j] + "  ");
                System.out.println();
            }
        } catch (NullPointerException ex){
            System.out.println("Null");
        }


    }
    // Trả mảng về dạng chuỗi để so sánh
    public String key(){
        if(this.data == null)
            return null;
        String s = "";
        for(var i : this.data)
            s += i.toString();
        return s;
    }


    @Override
    // Phương thức so sánh dùng để compare trong hàng đợi, sắp sếp tăng dần theo h + g
    public int compareTo(State o) {
        return (this.h + this.g) - (o.h + o.g);
    }
    // Phương thức đếm số ô sai so với trạng thái đích
    public int countCellWrong(Integer[] s){
        int count = 0;
        for(int i = 0; i < 9; ++ i){
            if(this.data[i] != s[i])
//                ++ count;
//                count += Math.abs(this.data[i] - i);
                count += Math.abs(data[i] - i) + Math.abs((data[i] - data[i] % 3) / 3 + data[i] % 3 - ((i - i % 3) / 3 - i % 3));
        }
        return count;
    }

    @Override
    // Nè ghi để debug nhìn cho dễ
    public String toString(){
        return this.key() + ",h(" + this.h +"), g(" + this.g + ")";
    }
}

