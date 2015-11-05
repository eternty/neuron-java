import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victoria on 14.10.2015.
 */
public class Net {

    private List<Neuron> net;
    private static List<LearnModule> toLearn;
    private boolean learned=false;
    private static List<Character> simbols;

    public Net() {
        net = new ArrayList<>();
        simbols=new ArrayList<>();
        Net.loadInput();
    }

    public static void saveInput(char simbol,List<Integer> list) {

        toLearn.add(0,new LearnModule(simbol,list));
        try(BufferedWriter out= new BufferedWriter(new FileWriter("Simple_Save_To_Learn",true)))
        {
            out.write(simbol);
            for (int i=0; i<list.size(); i++) {
                out.write(list.get(i)+"");
            }
            out.newLine();
        }catch (IOException ignore) {}

    }

    public static void loadInput() {

        toLearn=new ArrayList<>();
        try(BufferedReader in= new BufferedReader(new FileReader("Simple_Save_To_Learn")))
        {
            while (in.ready()) {
                List<Integer> list = new ArrayList<>();
                char simbol=(char)in.read();
                for (int i = 0; i < Window.getAmount(); i++) {
                    list.add(in.read()-48);
                }
                in.readLine();
                toLearn.add(new LearnModule(simbol,list));
            }
        }catch (IOException ignore) {}

    }

    public char recognize(List<Integer> input){
        double sum=Double.MIN_VALUE;
        char simbol=' ';
        for (Neuron x : net){
            double check=x.checkNeuron(input);
            if (check > sum) {
                sum=check;
                simbol=x.getSimbol();
            }

        }

        return simbol;
    }

    public void correctAllNeurons(char simbol, double speed, List<Integer> input) {

        saveInput(simbol,input);
        learned=false;
        while (!learned) {
            learned=true;
            for (LearnModule x : toLearn) {

                if (!simbols.contains(x.simbol)) {
                    net.add(new Neuron(x.simbol));
                    simbols.add(x.simbol);
                }
                teachAllNeurons(x.simbol, speed, x.list);
            }

            for (LearnModule x : toLearn)
                if (recognize(x.list)!=x.simbol) learned=false;
        }


    }

    private void teachAllNeurons(char simbol, double speed, List<Integer> input){

        for (Neuron x : net) {
            if (x.getSimbol()==simbol){

                while (recognize(input)!=simbol) {
                    x.correct(input, speed);
                    learned=false;
                }

            }
            // else x.correct(input, -1, speed);

            System.out.print(x.getSimbol() + ": ");
            double sum=0;
            for (int i=0; i< x.scales.size(); i++) {
                System.out.format("%f ,", x.scales.get(i));
                if (input.get(i)==1)
                    sum += x.scales.get(i);
            }
            System.out.println("  sum="+sum);
        }

        System.out.println();


    }

    private static class LearnModule {
        char simbol;
        List<Integer> list;

        public LearnModule(char simbol, List<Integer> list) {
            this.simbol = simbol;
            this.list = list;
        }

    }

}
