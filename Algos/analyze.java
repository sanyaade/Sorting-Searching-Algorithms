// A sorting method benchmarking utility
// by Madura A.
import java.util.UUID;
import java.util.Random;
import java.util.Scanner;

class analyze{
    public String array_types[] = {"random", "sorted", "reverse sorted"}; 
    public sort sorter;
    Scanner sc;
    analyze(){
        sorter = new sort();
        sc = new Scanner(System.in);
    }
    /* display a message to the user and get input between a and b inclusive */
    public int get_validated_input(String msg, int a, int b){
        int x=0;
        do {
            try{
                System.out.println(msg);
                x = sc.nextInt();
            } catch (Exception e){
                sc.next();
                x = a-1;
            }
        } while (x<a||x>b);
        return x;
    }
    /* display input type list and ask to pick one repeat on wrong input */
    public int get_input_type(){
        int inp=0;
        int i=0;
        do{
            try {
                System.out.println("Select the input type");
                for (i=0;i<sorter.types.length;i++)
                    System.out.println("\t"+(i+1)+". "+ sorter.types[i]);
                System.out.print("Enter selection : ");
                inp = sc.nextInt();
            } catch (Exception e) {
                sc.next();
                inp = 0;
            }
        } while (inp<1 || inp> sorter.types.length); // repeat on wrong input
        return inp-1;
    }
    
    public int get_array_type(){
        int inp=0;
        int i=0;
        do{
            try {
                System.out.println("Select the input array type");
                for (i=0;i<array_types.length;i++)
                    System.out.println("\t"+(i+1)+". "+array_types[i]);
                System.out.print("Enter selection : ");
                inp = sc.nextInt();
            } catch (Exception e) {
                sc.next();
                inp = 0;
            }
        } while (inp<1 || inp>array_types.length);
        return inp-1;
    }
    
    public int[] get_sort_method(){
        int inp=0;
        int i=0;
        do{
            try {
                System.out.println("Select sorting method");
                for (i=0;i<sorter.sorts.length;i++)
                    System.out.println("\t"+(i+1)+". "+sorter.sorts[i]);
                System.out.println("\t"+(++i)+". Some");
                System.out.println("\t"+(++i)+". All");
                System.out.print("Enter selection : ");
                inp = sc.nextInt()-1;
            } catch (Exception e){
                sc.next();
                inp = 0;
            }
        } while (inp<0 || inp>i-1);
        if (inp<sorter.sorts.length){
            int p[] = {inp};
            return p;
        } else if (inp==sorter.sorts.length){
            int valid = 1;
            int p[] = {};
            do {
                try {
                    System.out.println("Enter the sorts you want to include "
                                                        +"separated by commas");
                    String arr[] = sc.next().split(",");
                    p = new int[arr.length];
                    valid =1;
                    for (i=0;i<p.length;i++){
                        p[i]=Integer.parseInt(arr[i])-1;
                        if (p[i]<0 || p[i]>=sorter.sorts.length){
                            valid = 0;
                            break;
                        }
                    }
                } catch (Exception e) {
                    valid = 0;
                }
            } while (valid == 0);
            return p;
        } else {
            int p[] = new int[sorter.sorts.length];
            for (i=0;i<p.length;i++)
                p[i]=i;
            return p;
        }
    }
    /* make random array of given type and having elements with given bitcount*/
    public void random_array(XComparable[] array, int bit_count, int inpt){
        Random r = new Random();
        
        Double d = Math.pow(2, bit_count);
        switch (inpt){
            case 0: {
                for (int i=0;i<array.length;i++){
                    int u = r.nextInt(d.intValue()) | 0x80000000*(i%2);
                    Integer ix = new Integer(u);
                    array[i]=new XComparable(ix);
                    array[i].value = u; // primitive data for radix sort
                }
                break;
            }
            case 1: {
                for (int i=0;i<array.length;i++){
                    long u = (r.nextLong() | 0x8000000000000000l*(i%2) ) % d.intValue(); 
                    Long lx = new Long(u);
                    array[i]=new XComparable(lx);
                }
                break;
            }
            case 2: {
                for (int i=0;i<array.length;i++){
                    double u = r.nextDouble() * ((i%2)==0 ? 1:-1);
                    Double lx = new Double(u);
                    array[i]=new XComparable(lx);
                }
                break;
            }
            case 3: {
                for (int i=0;i<array.length;i++){
                    float u = r.nextFloat() * ((i%2)==0 ? 1:-1);
                    Float lx = new Float(u);
                    array[i]=new XComparable(lx);
                }
                break;
            }
            case 4: {
                for (int i=0;i<array.length;i++){
                    double u = r.nextDouble();
                    String lx = new String(UUID.randomUUID().toString());
                    array[i]=new XComparable(lx);
                }
                break;
            }
            case 5: {
                for (int i=0;i<array.length;i++){
                    int u = r.nextInt(255)%255;
                    Character lx = Character.forDigit(u,255);
                    array[i]=new XComparable(lx);
                    array[i].value = u;
                }
                break;
            }
        }
    }
    public void sorted_array(XComparable[] array, int bit_count, int inpt){
        random_array(array, bit_count, inpt);
        sorter.quicksortfast(array, 0, array.length-1);
    }
    public void reverse_sorted_array(XComparable[] array, int bit_count, int inpt){
        sorted_array(array, bit_count, inpt);
        for (int i=0;i<array.length/2;i++){
            sorter.swap(array, i, array.length-i-1);
        }
    }
    public void print_results(result res){
        System.out.println("\n\t--- RESULTS ---\n");
        
        for (int i=0;i<res.time.length;i++){
            System.out.println(sorter.sorts[i]);
            if (res.time[i]==-1)
                System.out.println("\t*** Check sorted failed ***");
            else if (res.time[i]==-2)
                System.out.println("\t*** Did not run ***");
            else
                System.out.println("\tRunning time : "+ res.time[i] + " ms");
            System.out.println();
        }
        System.out.println("\t--- ------- ---\n");

    }
    public void set_array(XComparable array[], int type, int bit_count, int inpt){
        switch (type){
            case 0: random_array(array, bit_count, inpt); return;
            case 1: sorted_array(array, bit_count, inpt); return;
            default: reverse_sorted_array(array, bit_count, inpt); return;
        }
    }
    public result[] do_bench_test(XComparable[] s, int methods[], int inp_type){
        int array_size = s.length;
        result res = new result(sorter);
        result resx[] = new result[1];

        XComparable ax[];
        resx[0] = res;
        ax = new XComparable[array_size];
        for (int i=0;i<methods.length;i++){
            if (sorter.check_blacklist(methods[i], inp_type)){
                res.time[i] = -2;
                continue;
            }
            sorter.copy(s, ax);
            long t = System.currentTimeMillis();
            sorter.sort_switch(ax, methods[i]);
            t = System.currentTimeMillis() - t;
            
            if (sorter.checksorted(ax) == false){
                res.time[methods[i]] = -1;
                continue;
            } else {
                res.time[methods[i]] += t;
            }        
        }
        sorter.copy(ax, s);
        return resx;
    }
    public result[] do_bench_test(int methods[], int type, int array_size,
                                                    int bit_count, int inp_type){
        Random r = new Random();
        result res = new result(sorter);
        result resx[] = new result[array_size+1];

        XComparable s[], ax[];
        for (int j=0;j<array_size;j++){
            s = new XComparable[j];
            ax = new XComparable[j];
            set_array(s, type, bit_count, inp_type);
            resx[j] = new result(sorter);
            for (int i=0;i<methods.length;i++){
                if (sorter.check_blacklist(methods[i], inp_type)){
                    res.time[i] = -2;
                    continue;
                }
                sorter.copy(s, ax);
                long t = System.currentTimeMillis();
                sorter.sort_switch(ax, methods[i]);
                t = System.currentTimeMillis() - t;
               
                if (sorter.checksorted(ax) == false){
                    res.time[methods[i]] = -1;
                    resx[j].time[methods[i]] = -1;
                    continue;
                } else {
                    res.time[methods[i]] += t;
                    resx[j].time[methods[i]] = t;
                }
            
            }
        }
        
        resx[array_size] = res;
        return resx;
    }
    public static void main(String xx[]){
        analyze a = new analyze();
        int f[];
        int i=0,j=0,x=0,b=0,t=0;
        boolean file_mode = false;
        System.out.println();
        System.out.println("***************************");
        System.out.println("* Sorting method analyzer *");
        System.out.println("*       by Madura A.      *");
        System.out.println("***************************");
        
        do {
            if ((xx.length>0) && (xx[0]!="")){
                file_mode = true;
            } else {
                System.out.println();
                i = a.get_array_type();
                System.out.println();
                x = a.get_validated_input("Specify max array size", 2, 0x0FFFFFFF);
                System.out.println();
                b = a.get_validated_input("Specify bit size", 1, 32);
            }
            System.out.println();
            t = a.get_input_type();
            System.out.println();
            f = a.get_sort_method();
            String title = a.array_types[i]+" arrays from size 0 to "+ 
                                            x + " of "+a.sorter.types[t]+"s";
            System.out.println();
                
            result u[];
            if (file_mode){
                file fx = new file();
                XComparable tmp[] = fx.from_file(xx[0],t);
                if (tmp==null){
                    System.out.println("Err: Failed to make array from file");
                    System.exit(1);
                }
                System.out.print("Sorting a given list of "+ a.sorter.types[t] 
                                                                    +"s ... ");
                u = a.do_bench_test(tmp, f, t);
                System.out.println("Done.");
                fx.to_file(tmp,"Results.txt");
                a.print_results(u[x]);
            } else {
                System.out.print("Running tests for " + title +" ... ");
                u = a.do_bench_test(f, i, x, b, t);
                System.out.println("Done.");
                a.print_results(u[x]);
            }
            
            if (u.length > 2){
                int cummu = a.get_validated_input("Pick a graph to draw\n"+
                    "\t1. y=time, x=input size\n" +
                    "\t2. y=cummulated time, x=input size\n"+
                    "\t3. Both\n"+
                    "Enter choice",1,3);
                System.out.print("\nDrawing ... ");
                if (cummu==3) {
                    graph g1 = new graph(u, f, false);
                    grapht t1 = new grapht(title, g1);
                    t1.start();
                    
                    graph g2 = new graph(u, f, true);
                    grapht t2 = new grapht(title, g2);
                    t2.start();
                    
                } else {
                    graph g1 = new graph(u, f, cummu==1?false :true);
                    grapht t1 = new grapht(title, g1);
                    t1.start();
                }
                System.out.println("Done.");
            }
            
            System.out.println("Press Enter to continue or Esc and Enter to exit");
            try{
                if (System.in.read() == 27)
                    System.exit(0);
            } catch (Exception e){
                break;
            }
        } while (file_mode == false);
    }
}
