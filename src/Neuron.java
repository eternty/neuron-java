import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victoria on 11.10.2015.
 */
public class Neuron {

    public List<Double> scales;
    private char simbol;

    public char getSimbol() {
        return simbol;
    }

    public Neuron(char simbol) {
        scales=new ArrayList<>();
        for (int i=0; i< Window.getAmount(); i++ )
            scales.add(Math.random() % 0.4 - 0.2);
        this.simbol=simbol;
    }




    public double checkNeuron( List<Integer> input) {
        double sum=sum(input);
        if (sum>0) return sum;
        return 0;



    }

    private double sum (List<Integer> input) {
        double sum=0;
        for (int i=0; i< scales.size(); i++){
            sum+=scales.get(i)*input.get(i);
        }
        return sum;
    }


    public void correct(List<Integer> correction, double speed) {
        for (int i=0; i<scales.size(); i++){
            double error=correction.get(i)-scales.get(i);
            scales.set(i, scales.get(i)+ speed*error*correction.get(i)   );
        }

    }

}
