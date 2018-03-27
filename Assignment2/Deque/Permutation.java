
import edu.princeton.cs.algs4.StdIn;

public class Permutation
{
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();

           while (!StdIn.isEmpty())
               randQueue.enqueue(StdIn.readString());

           
           String[] aux = new String[randQueue.size()];

        for(int i = 0; !randQueue.isEmpty(); ++i)
            aux[i] = randQueue.dequeue();

           for(int i = 0; i < aux.length; ++i)
           {
               System.out.println(aux[i]);
           }
    }

    }

