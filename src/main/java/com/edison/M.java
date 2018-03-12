package com.edison;

public class M {

    public static void main(String[] args) {
        A a1 = new A();

        System.out.println(a1.i);
        System.out.println(a2.i);
        System.out.println(findNextPrime(103));
    }

    static int findNextPrime(int i) {
        outer:
        for(int k=i+1;;k++){
            if(k%2 == 0){
                continue;
            }
            for(int j=3;j<=Math.sqrt(k);j+=2){
                if(k%j == 0){
                    continue outer;
                }
            }
            return k;
        }
    }


    static A a2 = new A();

}
class A {
    A() { i = (j++ != 0) ? ++j : --j; }
    public int i;
    public static int j = 0;
}
