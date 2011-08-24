// Sorter library
// by Madura A.

class sort{
    public String sorts[] = {"Quicksort - Partition using new array", 
                            "Quicksort - Partition inplace, pivot=start", 
                            "Quicksort - Partition inplace, pivot=middle",
                            "Heapsort", "Straight Radix Sort", 
                            "Exchange Radix Sort", "Shell sort"};
    public String types[] = { "Integer", "Long", "Double", "Float",
                                    "String", "Character" };
    /* blacklisted types {method, type} */
    public int[][] blacklist_types = { {4,1}, {4, 2}, {4, 3}, {4, 4},  
                                        {5,1}, {5, 2}, {5, 3}, {5, 4} };
    /* Radix sort */
    private int max_bit;
    /* Iterative method to sort file by straight radix sort */
    public void straight_radixsort(XComparable ax[]){
        int maxbit=0;
        for (int i=0;i<ax.length;i++){
            // get maximum bit length for a given number set
            int x = (int)Math.round(Math.log(ax[i].value)/Math.log(2))+1;
            if (maxbit<x)
                maxbit = x;
        }
        if (maxbit>32)
            maxbit = 32;
        max_bit = maxbit-1;
        for (int i=0;i<maxbit;i++)
            straight_radixsort(ax, i);
    }
    /* Iterative method to sort file by exchange radix sort */
    public void exchange_radixsort(XComparable ax[]){
        int maxbit=0;
        for (int i=0;i<ax.length;i++){
            int x = (int)(Math.round(Math.log(ax[i].value)/Math.log(2)))+2;
            if (maxbit<x)
                maxbit = x;
        }
        if (maxbit>32)
            maxbit = 32;
        max_bit = maxbit-1;
        exchange_radixsort(ax, 0, ax.length-1, max_bit);
    }
    
    /* Straight radix sort that uses the iterative bit sorting method */
    private void straight_radixsort(XComparable ax[], int bit){
        int write=0;
        for (int i=0;i<ax.length;i++){
            if (getbit(bit, ax[i].value) == 0){
                XComparable tmp = ax[i];     
                for (int j=i;j>write;j--){
                    ax[j] = ax[j-1];
                }
                ax[write] = tmp;
                write++;
            }
        }
    }
    /* Exchange radix sort
     * Uses the partitioning method of quicksort to partition by bits */
    private void exchange_radixsort(XComparable ax[], int a, int b, int bit){
        if (a>=b || a<0 || bit<0)
            return;
        // swap pivot to a 
        swap(ax, a, (a+b)/2);
        int pivot=a;
        int al=a,ah=b;
        while (true){
            while ((al<b) && ( getbit(bit, ax[al].value) == 0 ))
                al++;
            while ((ah>a) && ( getbit(bit, ax[ah].value) == 1 ))
                ah--;
            if (al<ah){
                swap(ax, al, ah);
            } else
                break;
        }
        swap(ax, pivot, ah);

        if (getbit(bit, ax[pivot].value) == 1){
            exchange_radixsort(ax, a, ah-1, bit - 1);
            exchange_radixsort(ax, ah, b, bit - 1);
        } else {
            exchange_radixsort(ax, a, ah, bit - 1);
            exchange_radixsort(ax, ah+1, b, bit - 1);
        }
    }
    /* Get bit by position */
    private int getbit(int pos, int i){
        int msb = i>>>31;
        if (pos==max_bit){
            if (msb == 0){ 
                return 1; // return msb as 1 if the number is positive or 0
            } else {
                return 0; // return msb as 0 if the number is negative
            }
        }
        return (i<<(31-pos))>>>31; // return (msb>)p positions 
    }
    /* Shell sort, using preset sub array sizes */
    public void shellsort(XComparable ax[]){
        int subsize[] = {1, 3, 7, 21, 48, 112, 336, 861, 1968, 4592, 13776, 
                                                        33936, 86961, 198768};
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
                    int j;
                    XComparable m;
                    m = ax[h];
                    j=r-1;
                    // do insertion sort inside the sub array
                    while ((j>=0) && (ax[j*subsize[i]+c].compareTo(m) > 0)){
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
    
    /* Methods for Quicksort */
    
    /* a fast method for inplace partitioning, the pivot must be the
     * first element */
    private int partitionfast2(XComparable ax[], int a, int b){
        int pivot=a;
        XComparable part = ax[pivot];
        int al=a,ah=b;
        while (true){
            while ((al<b) && ( ax[al].compareTo(part)<=0 ))
                al++;
            while ((ah>a) && ( ax[ah].compareTo(part)>0 ))
                ah--;
            if (al<ah){
                swap(ax, al, ah);
            } else
                break;
        }
        swap(ax, pivot, ah);
        return ah;
    }
    /* a fast method for inplace partitioning */
    private int partitionfast(XComparable ax[], int a, int b){
        // swap pivot to first place, and use the method thats for pivot=low
        swap(ax, a, (a+b)/2); 
        int pivot=a;
        XComparable part = ax[pivot];
        int al=a,ah=b;
        while (true){
            while ((al<b) && ( ax[al].compareTo(part)<=0 ))
                al++;
            while ((ah>a) && ( ax[ah].compareTo(part)>0 ))
                ah--;
            if (al<ah){
                swap(ax, al, ah);
            } else
                break;
        }
        swap(ax, pivot, ah);
        return ah;
    }
    /* A slow method for paritioning using a separate array*/
    private int partition(XComparable ax[], int a, int b){
        if (a==b)
            return a;
        int part = (b+a)/2; // set pivot as the middle item
        int al=0,ah=b-a,i,j;
        XComparable axl[] = new XComparable[b-a+1];

        for (i=a;i<=b;i++){
            if (i != part){
                if (ax[i].compareTo(ax[part])<0)
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
    /* Recursive method for quicksort, pivot=(low+high)/2 */
    public void quicksortfast(XComparable ax[], int a, int b){
        if (a<b){
            int x = partitionfast(ax, a, b);
            quicksortfast(ax, a, x-1);
            quicksortfast(ax, x+1, b);
        }
    }
    /* Recursive method for quicksort, pivot=a */
    public void quicksortfast2(XComparable ax[], int a, int b){
        if (a<b){
            int x = partitionfast2(ax, a, b);
            quicksortfast2(ax, a, x-1);
            quicksortfast2(ax, x+1, b);
        }
    }
    /* Recursive method for quicksort, new array partitioning */
    public void quicksort(XComparable ax[], int a, int b){
        if (a<b){
            int x = partition(ax, a, b);
            quicksort(ax, a, x-1);
            quicksort(ax, x+1, b);
        }
    }


    /* heap sort */
    public void heapsort(XComparable ax[]){
        heap h = new heap(ax);
        h.heapsort();
    }
    
    
    /* other utility methods */
    public void swap(XComparable ax[], int i, int j){
        XComparable tmp = ax[i];
        ax[i] = ax[j];
        ax[j] = tmp;
    }
    public void print(XComparable ax[]){
        for (int i=0;i<ax.length;i++)
            System.out.print(ax[i].com+", ");
        System.out.println();
    }
    /* checks whether a given array is sorted or not */
    public boolean checksorted(XComparable ax[]){
        for (int i=1;i<ax.length;i++)
            if (ax[i-1].compareTo(ax[i])>0)
                return false;
        return true;
    }
    public void copy(XComparable[] arr0, XComparable[] arr1){
        for (int i=0;i<arr0.length;i++)
            arr1[i] = arr0[i];
    }
    public void copy(XComparable[] arr0, XComparable[] arr1, int len){
        for (int i=0;i<len;i++)
            arr1[i] = arr0[i];
    }
    public XComparable[] copy(XComparable[] arr){
        XComparable[] ax = new XComparable[arr.length];
        for (int i=0;i<arr.length;i++)
            ax[i] = arr[i];
        return ax;
    }
    /* check for blacklisted (method, input-type) tuples */
    public boolean check_blacklist(int method, int inp_type){
        for (int i=0;i<blacklist_types.length;i++){
            if (method==blacklist_types[i][0] && inp_type==blacklist_types[i][1])
                return true;
        }
        return false;
    }
    /* sort methods 
     * 0 quicksort - new array
     * 1 quicksort - pivot=a
     * 2 quicksort - pivot=(a+b)/2
     * 3 heapsort
     * 4 straight radix sort
     * 5 exchange radix sort
     * 6 shellsort
     * */
    public void sort_switch(XComparable cp[], int s){
        int len = cp.length -1; // do the calculation everytime in order to have
                                // the same effect on every sort method
        switch (s){
            case 0: quicksort(cp, 0, len); break;
            case 1: quicksortfast2(cp, 0, len); break;
            case 2: quicksortfast(cp, 0, len); break;
            case 3: heapsort(cp); break;
            case 4: straight_radixsort(cp); break;
            case 5: exchange_radixsort(cp); break;
            case 6: shellsort(cp); break;
            default:
                return;
        }
    }
    
}
