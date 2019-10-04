package pardisLab3;

import java.util.Random;

public class pardisLab3Main {

  static void populate(LazySkipList<Integer> lsl) {
    Random rng = new Random();
    Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
    for (int i = 0; i < t.length; i++) {
      t[i] = new Thread() {
        public void run() {
          for (int i = 0; i < 10000000 / t.length; i++) {
            lsl.add(rng.nextInt());
          }
        }
      };
      t[i].start();
    }
    for (int i = 0; i < t.length; i++) {
      try {
        t[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LazySkipList<Integer> list1 = new LazySkipList<Integer>();
		
		populate(list1);
	}

}
