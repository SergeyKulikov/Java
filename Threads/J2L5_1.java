import java.util.ArrayList;
import java.util.Arrays;

/**
 * Проверка когда скорость расчета растет, а когда ничинает падать
 *
 * @author Sergey Kulikov
 * @version 1.00 on 23.06.2017
 */

class J2L5_1 {
	static final int SIZE = 10000000;			 	// Размер массива для обработки
	
	public static void main(String args[]) {
		// ------------------------------------------------
	    float[] arr = new float[SIZE];		 				// Массив для одного потока
		
		long finishTime = 0; // Фигня для расчета времени и результата
		long passedTime = 0;
		long time1, time2; 
		long startTime = System.currentTimeMillis();
		boolean rez = false;
		
		// one thread
		fillArray(arr);	// Залили еднички в массив
		MyThread v1 = new MyThread();
		v1.arr = arr;
		v1.start();
		while (v1.isAlive()); // Ждем окончания потока, а то неправильно выведет время расчета
		finishTime = System.currentTimeMillis();
		time1 = finishTime-startTime;
		System.out.println("Totally completed in " + (time1)+ " msec.\n");
		
		for (int t=50; t<=600; t+=50) 
			threadCalcalate(t, v1.arr, time1);
		
    }		
		
	public static void threadCalcalate(int threads, float [] arr, long time1) {
		
		int THREADS = (threads == 0) ? 1 : threads;				 	// Количество потоков (может больше кол-ва ядер)
		//int SIZE = 10000000;			 	// Размер массива для обработки
		int H = SIZE / THREADS;		 	// Делим массив на количество потоков
		int HR = SIZE-(H * THREADS);	 	// Возможно есть остаток от SIZE

		long finishTime = 0; // Фигня для расчета времени и результата
		long passedTime = 0;
		long time2; 
		long startTime = System.currentTimeMillis();
		boolean rez = false;

		// ------------------------------------------------ 
		float[] arrThreads = new float[SIZE];				// Массив для нескольких потоков	    
	    ArrayList<MyThread> tr = new ArrayList<MyThread>();	// Будем хранить потоки здесь
		
		float[][] arrays = new float[THREADS][H]; 	// создаем массивы под кадый поток размерностью SIZE / THREADS
		float[] arraysh = new float[HR]; 			// Массив, в который пишем хвостик массива, который получился от SIZE-(H * THREADS) 		
		
		// multy thread
		fillArray(arrThreads); // Залили еднички в массив
		startTime = System.currentTimeMillis();
		for (int t=0; t<THREADS; t++) {
			System.arraycopy(arrThreads, H*t, arrays[t], 0, H);
			
			tr.add(new MyThread());
			tr.get(t).arr = arrays[t];
			tr.get(t).startElement = H*t;
			tr.get(t).passedTime = 0;
			tr.get(t).start();
		}
		
		for (int t=0; t<THREADS; t++) {
			try {
				tr.get(t).join();
			} catch (InterruptedException ex) {
				System.out.println(tr.get(t).getName()+" thread is interrupted.");
			}
		} // Ждем окончания всех созданных потоков, а то неправильно выведет время расчета

		if (HR > 0) { // Уже после всех потоков обрабатываем хвост. мне так удобнее.
			int t = THREADS;
			System.arraycopy(arrThreads, H*t, arraysh, 0, HR);  // Размерность массива другая! (HR вместо H)
			
			tr.add(new MyThread());
			tr.get(t).arr = arraysh;
			tr.get(t).startElement = H*t;
			tr.get(t).passedTime = 0;
			tr.get(t).start();
			try {
				tr.get(t).join();
			} catch (InterruptedException ex) {
				System.out.println(tr.get(t).getName()+" thread is interrupted.");
			}
		} // Ждем окончания потока, а то неправильно выведет время расчета

		for (int t=0; t<THREADS; t++) {
			System.arraycopy(tr.get(t).arr, 0, arrThreads, H*t, H); // Собираем полный массив из кусочков
		}
		
		if (HR > 0) { 
			int t = THREADS;
			System.arraycopy(tr.get(t).arr, 0, arrThreads, H*t, HR); // Дописываем хвост
		}
		
		finishTime = System.currentTimeMillis();
		
		time2 = finishTime-startTime;
		System.out.println("Totally completed in " + (time2)+ " msec for "+THREADS+" threads.");
		System.out.printf("\nThreads work in %f times faster!", (float)((float)time1/(float)time2));
		
		rez = Arrays.equals(arr, arrThreads); // Сравнили результаты прямого расчета и по кускам.
		System.out.printf("\nThe values of the arrays are%s the same.", rez ? "" : "nt\'t");
    }
	
	public static void fillArray(float []arr) {
		for (int i=0; i<arr.length; i++)
			arr[i] = 1; 
	}

}


class MyThread extends Thread {
	public float[] arr;
	long passedTime;
	int startElement;
		
	public void countArray() {
		for (int i=0; i<arr.length; i++) {
			arr[i] = (float)(arr[i] * Math.sin(0.2f + (i+startElement) / 5) * Math.cos(0.2f + (i+startElement) / 5) * Math.cos(0.4f + (i+startElement) / 2)); 
		}
	}
	
	@Override
	public void run() {
        // System.out.println(getName() + " has started.");
		
		long a = System.currentTimeMillis();
		countArray();
		passedTime = System.currentTimeMillis()-a;
		// System.out.println(getName() + " completed in " + (passedTime)+ " msec.");
	}	
}