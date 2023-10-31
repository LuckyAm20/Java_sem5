class MyClass {
    private int value;

    public MyClass(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public void swap(MyClass other) {
        int temp = this.value;
        this.value = other.value;
        other.value = temp;
    }
}

public class Main {
    public static void main(String[] args) {
        MyClass obj1 = new MyClass(5);
        MyClass obj2 = new MyClass(10);

        System.out.println("Before:");
        System.out.println("Obj 1: " + obj1.get());
        System.out.println("Obj 2: " + obj2.get());

        obj1.swap(obj2);

        System.out.println("After:");
        System.out.println("Obj 1: " + obj1.get());
        System.out.println("Obj 2: " + obj2.get());
    }
}

