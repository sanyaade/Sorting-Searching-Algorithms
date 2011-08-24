// A result of a test is returned in this Object
// by Madura A.

class result{
    long time[]; // the runnning time of the sorts, -1 denotes a failed sort
    result(sort s){
        time = new long[s.sorts.length];
    }
    
}
