import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;

/*
 * https://www.reddit.com/r/dailyprogrammer/comments/6jr76h/20170627_challenge_321_easy_talking_clock/
 */
public class Main {
    private final static String[] HOURS = {"12.wav", "1.wav", "2.wav", "3.wav", "4.wav", "5.wav", "6.wav", "7.wav", "8.wav", "9.wav", "10.wav", "11.wav", "12.wav", "1.wav", "2.wav", "3.wav", "4.wav", "5.wav", "6.wav", "7.wav", "8.wav", "9.wav", "10.wav", "11.wav"};
    private final static String[] MINUTE_FIRST = {"o.wav", "10.wav", "20.wav", "30.wav", "40.wav", "50.wav"};
    private final static String[] MINUTE_SECOND = {"", "1.wav", "2.wav", "3.wav", "4.wav ", "5.wav ", "6.wav", "7.wav", "8.wav", "9.wav"};

    private static void playAudioFile(String file) {
        try {
            File yourFile = new File("audio/" +file);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter hour: ");
        String input = scanner.nextLine();

        ArrayList<String> result = new ArrayList<>(); // Will save here the audio file names and play them all at the end.
        result.add("its.wav");
        String timeDay = "";
        String[] split = input.split(":");

        int hour = Integer.parseInt(split[0]);
        int[] minutes = new int[2];
        minutes[0] = Integer.parseInt(split[1].substring(0, 1));
        minutes[1] = Integer.parseInt(split[1].substring(1));

        result.add(HOURS[hour]);
        timeDay = hour < 12 ? "AM.wav" : "PM.wav";

        if(minutes[0] == 1 && minutes[1] != 0) { // Irregular numbers
            switch(minutes[1]) {
                case 1:
                    result.add("11.wav");
                    break;
                case 2:
                    result.add("12.wav");
                    break;
                case 3:
                    result.add("13.wav");
                    break;
                case 4:
                    result.add("14.wav");
                    break;
                case 5:
                    result.add("15.wav");
                    break;
                case 6:
                    result.add("16.wav");
                    break;
                case 7:
                    result.add("17.wav");
                    break;
                case 8:
                    result.add("18.wav");
                    break;
                case 9:
                    result.add("19.wav");
                    break;
            }
        } else {
            if(!(minutes[0] == 0 && minutes[1] == 0)) { // To prevent 'It's nine oh pm'.
                result.add(MINUTE_FIRST[minutes[0]]);
            }
            result.add(MINUTE_SECOND[minutes[1]]);
        }
        result.add(timeDay);

        for (String s : result) {
            try {
                if(!s.matches("")) {
                    playAudioFile(s);
                    Thread.sleep(600);
                }
            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
