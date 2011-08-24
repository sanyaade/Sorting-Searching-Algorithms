// A wrapper class that binds raw data and comparability of java
// by Madura A.
 
class XComparable{
    public int value;
    public Comparable com;
    XComparable(Comparable c){
        com = c;
        value = 0;
    }
    
    @SuppressWarnings("unchecked")
    int compareTo(XComparable t){
        return com.compareTo(t.com);
    }
}
