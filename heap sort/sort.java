class sort{
    public boolean debug;
    private void swap(int ax[], int i, int j){
        int tmp = ax[i];
        ax[i] = ax[j];
        ax[j] = tmp;
    }
    public void radixsort(int ax[], int maxbit, boolean straight){
        if (straight){
            for (int i=0;i<maxbit;i++)
                straight_radixsort(ax, i);
        } else {
            exchange_radixsort(ax, 0, ax.length-1, maxbit -1);
        }
    }
    private void straight_radixsort(int ax[], int bit){
        int write=0;
        for (int i=0;i<ax.length;i++){
            if (getbit(bit, ax[i]) == 0){
                int tmp = ax[i]; // to be     
                for (int j=i;j>write;j--){
                    ax[j] = ax[j-1];
                }
                ax[write] = tmp;
                write++;
            }
        }
    }
    private int partitionfast2(int ax[], int a, int b){
        int pivot=a,part = ax[pivot];
        int al=a,ah=b;
        while (true){
            while ((al<b) && (ax[al]<=part))
                al++;
            while ((ah>a) && (ax[ah]>part))
                ah--;
            if (al<ah){
                swap(ax, al, ah);
            } else
                break;
        }
        swap(ax, pivot, ah);
        return ah;
    }
    private void exchange_radixsort(int ax[], int a, int b, int bit){ // exchange radix
        if (a>=b || a<0 || bit<0)
            return;
        int pivot = (a+b)/2;
        int write = a;
        swap(ax, pivot, b);
        for (int i=a;i<b;i++)
            if (getbit(bit, ax[i]) == 0){
                swap(ax, i, write);
                write++;
            }
        swap(ax, write, b);

        if (getbit(bit, ax[write]) == 1){
            exchange_radixsort(ax, a, write-1, bit - 1);
            exchange_radixsort(ax, write, b, bit - 1);
        } else {
            exchange_radixsort(ax, a, write, bit - 1);
            exchange_radixsort(ax, write+1, b, bit - 1);
        }
        
    }
    private int getbit(int pos, int i){
        return (i<<(31-pos))>>>31;
    }
    public void p(int x){
        if (debug)
            System.out.println(x);
    }
    public void p(String x){
        if (debug)
            System.out.println(x);
    }
    public void p(int x[]){
        if (debug)
            print(x);
    }
    public void shell(int a[]) {
        int increment = a.length / 2;
        while (increment > 0) {
            
            for (int i = increment; i < a.length; i++) {
                int j = i;
                p(i);
                int temp = a[i];
                while (j >= increment && a[j - increment] > temp) {
                    a[j] = a[j - increment];
                    j = j - increment;
                }
                a[j] = temp;
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
    }
    public void shellsort(int ax[]){
        int subsize[] = {1, 3, 7, 21, 48, 112, 336, 861, 1968, 4592, 13776, 33936, 86961, 198768};
        int k;
        k=0;
        for (int i=0;i<subsize.length;i++){
            if ((ax.length - subsize[i])>2)
                k = i;
        }
        int r,c,h;
        for (int i=k;i>=0;i--){
            for (c=0;c<subsize[i];c++){
                r=1;
                h=r*subsize[i]+c;
                while (h<ax.length){
                    int m,j;
                    m = ax[h];
                    j=r-1;
                    while ((j>=0) && (ax[j*subsize[i]+c]>m)){
                        ax[(j+1)*subsize[i]+c] = ax[j*subsize[i]+c];
                        j--;
                    }
                    ax[(j+1)*subsize[i]+c] = m;
                    r++;
                    h=r*subsize[i]+c;
                    
                }
            }
            
        }
    }
    private int partitionfast(int ax[], int a, int b){
        int pivot = (a+b)/2;
        int pivotVal = ax[pivot];
        int write = a;
        swap(ax, pivot, b);
        for (int i=a;i<b;i++)
            if (ax[i]<pivotVal){
                swap(ax, i, write);
                write++;
            }
        swap(ax, write, b);
        return write;
    }
    private int partition(int ax[], int a, int b){
        if (a==b)
            return a;
        int part = (b+a)/2;
        int al=0,ah=b-a,i,j;
        int axl[] = new int[b-a+1];

        for (i=a;i<=b;i++){
            if (i != part){
                if (ax[i] < ax[part])
                    axl[al++] = ax[i];
                else
                    axl[ah--] = ax[i];
            }
        }
        axl[ah] = ax[part];        
        for (j=0;j<axl.length;j++)
            ax[a+j] = axl[j];
        
        return a+ah;
    }
    public void print(int ax[]){
        for (int i=0;i<ax.length;i++)
            System.out.print(ax[i]+", ");
        System.out.println();
    }
    private void quicksort(int ax[], int a, int b){
        if (a<b){
            int x = partitionfast(ax, a, b);
            quicksort(ax, a, x-1);
            quicksort(ax, x+1, b);
        }
    }
    public void quicksort(int ax[]){
        quicksort(ax, 0, ax.length-1);
    }
    public void selectionsort_dual(int ax[]){
        int maxi, mini;
        for (int i=1;i<ax.length/2;i++){
            maxi=mini=i;
            for (int j=i-1;j<ax.length-i;j++){
                if (ax[maxi] < ax[j])
                    maxi = j;
                if (ax[mini] > ax[j])
                    mini = j;
            }
            swap(ax, maxi, ax.length - i);
            swap(ax, mini, i-1);
            
        }
    }
    public void selectionsort(int ax[]){
        int k=0;
        for (int i=0;i<ax.length-1;i++){
            k=i;
            for (int j=i+1;j<ax.length;j++){
                if (ax[j] < ax [k])
                    k = j;
            }
            swap(ax, k, i);
        }
    }
    public void insertionsort(int ax[]){
        int m,j;
        for (int i=1;i<ax.length;i++){
            m = ax[i];
            j=i-1;
            while ((j>=0) && (ax[j]>m)){
                ax[j+1] = ax[j];
                j--;
            }
            ax[j+1] = m;
        }
    }
    private boolean checksorted(int ax[]){
        for (int i=1;i<ax.length;i++)
            if (ax[i-1]>ax[i])
                return false;
        return true;
    }
    public void heapsort(int ax[]){
        heap h = new heap(ax);
        h.heapsort();
    }
    private int[] copy(int[] arr){
        int[] ax = new int[arr.length];
        for (int i=0;i<arr.length;i++)
            ax[i] = arr[i];
        return ax;
    }
    public int checksort_algo(int symbols[], int depth, int ax[]){
        if (depth==symbols.length)
            return 1;
        int cp[] = copy(ax);
        //radixsort(cp,5,false);
        //cp = copy(ax);
        //shellsort(cp);
        //cp = copy(ax);
    
        quicksort(cp);
        print(cp);
        //cp = copy(ax);
        //heapsort(cp);
        if (!checksorted(cp)){
            System.out.println("Fail");
            print(ax);
            print(cp);
            return 0;
        }
        int ret=1;
        for (int i=0;i<symbols.length;i++){
            ax[depth]=symbols[i];
            if (ret==1)
                ret = checksort_algo(symbols, depth+1, ax);
            else
                return 0;
        }
        return 1;
    }
    public static void main(String d[]){
        sort a = new sort();
        int arr[] = {15,14,8,5,1,3};
        int ax[] = new int[arr.length];
        a.debug = true;
        a.p(a.getbit(2,5));
        //System.out.println(a.partitionfast(arr,0,7));
       /* a.radixsort(arr,0);
        a.print(arr);
        a.radixsort(arr,1);
        a.print(arr);
        a.radixsort(arr,2);
        a.radixsort(arr,3);
        */
        //a.checksort_algo(arr, 0, ax);
        //System.out.println(a.getbit(2,5));
       // a.radixsort(arr,0,ax.length-1,2);
        //a.radixsort(arr,0,7,2);
        //a.radixsort(arr,0,7,3);
        //a.radixsort(arr,0,7,4);
        
        
        //a.shell(arr);
        //a.quicksort(arr);
        //a.print(arr);
        
    }
}
