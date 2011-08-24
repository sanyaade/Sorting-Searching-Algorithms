class useheap{
    public static void main(String ax[]){
        int h[] = {7,0,0,1,0,0,0,0};
        heap x = new heap(h);

        x.heapsort();
        x.print();
    }
}
