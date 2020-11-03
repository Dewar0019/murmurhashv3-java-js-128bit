import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

public class REPL {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    static final int ITERATIONS = 100000;

    public static void main(String[] args) throws Exception {
        System.out.println("Generating ");
        String dir = System.getProperty("user.dir");

        File genText = new File(dir + "/inputText.txt");
        File outputHash = new File(dir + "/outputHash.txt");
        FileOutputStream genTextStream = new FileOutputStream(genText);
        FileOutputStream outputHashStream = new FileOutputStream(outputHash);

        Random rand = new Random();
        for(int i = 0; i< ITERATIONS; i++) {
            String generatedString = randomString(Math.abs(rand.nextInt(2000-1)) + 1);
            if(generatedString.length() == 0) {
                throw new Exception("Something went wrong and got empty string");
            }
            String str = i == ITERATIONS - 1 ? generatedString : generatedString + ",";
            genTextStream.write(str.getBytes());
            MurmurHash3.HashValue hash = MurmurHash3.murmurhash3_x64_128(generatedString, 0);
            System.out.print(".");
            try {
                str = i == ITERATIONS - 1 ? hash.toString() : hash.toString() + ",";
                outputHashStream.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Finished");
    }

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}