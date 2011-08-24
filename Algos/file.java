// Read file and make an XComparable array and write XComparable array to file
// by Madura A.

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

class file{
    public void to_file(XComparable ax[], String f){
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(f));
            
            for (int i=0;i<ax.length;i++){
                br.write(ax[i].com + ", ");
            }
            br.flush();
        } catch (Exception e){
            System.out.println("Err: File write failed");
        }
    }
    public XComparable[] from_file(String f, int type){
        XComparable x[];
            
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            String token;
            int a=br.read();
            int count = 0;
            while (a!=-1) {
                if (Character.toChars(a)[0] == ',')
                    count++;
                a=br.read();
            }
            br = new BufferedReader(new FileReader(f));
            a=br.read();
            x =  new XComparable[count];
            count = 0;
            String tmp=new String("");
            while (a!=-1) {
                char ax[] = Character.toChars(a);
                XComparable t;
                if (ax[0] == ','){
                    switch (type){
                        case 0:{ int b = Integer.parseInt(tmp);
                                t = new XComparable(new Integer(b));
                                t.value = b;

                            break; }
                        case 1:{ long b = Long.parseLong(tmp);
                                t = new XComparable(new Long(b));
                            break; }
                        case 2:{ double b = Double.parseDouble(tmp);
                                t = new XComparable(new Double(b));
                            break; }
                        case 3:{ float b = Float.parseFloat(tmp);
                                t = new XComparable(new Float(b));
                            break; }
                        
                        case 5:{ char b = tmp.charAt(0);
                                t = new XComparable(new Character(b));
                                t.value = (int)b;
                            break; }
                        default :{ 
                                t = new XComparable(tmp);
                            break; }
                    }
                    x[count] = t;
                    count++;
                    tmp = new String("");
                } else
                    tmp += new String(ax);
                a=br.read();
            }
        } catch (Exception io){
            x = null;
        }
        return x;
    }
}


