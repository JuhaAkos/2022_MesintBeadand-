package pack;

import java.util.ArrayList;

public class GeneticAlgorithm {
	
	public static void main(String[] args) {
		ArrayList<int[][]> generation;
		
		final long startTime = System.nanoTime();
		double duration = 0;
		double fullDuration = 0;
			
		/*generation = runOnegeneration(FlowShop.generateRandomData(500, 20));
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 20, 5));
		duration = ((double) (System.nanoTime() - startTime))/1000000000;
		System.out.println("Time taken: " + duration + "sec");
		FlowShop.drawMatrix(generation.get(0));	
		*/		
		
		//base data
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 5, 4));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		//FlowShop.drawMatrix(generation.get(0));		
		
		//M=5 J=10
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 10, 5));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		//FlowShop.drawMatrix(generation.get(0));
		
		//FlowShop.drawDiagram(generation.get(0));			
		
		
		//M=5 J=20
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 20, 5));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		//FlowShop.drawMatrix(generation.get(0));		
		
		//M=10 J=50
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 50, 10));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		//FlowShop.drawMatrix(generation.get(0));	
		
		//M=10 J=100
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(),100, 10));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		//FlowShop.drawMatrix(generation.get(0));	
		
		//M=20 J=200
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 200, 20));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		//FlowShop.drawMatrix(generation.get(0));	
		
		//M=20 J=500
		generation = runOnegeneration(StoreMatrix.generateSmallerData( StoreMatrix.getData(), 500, 20));
		fullDuration = (((double) (System.nanoTime() - startTime))/1000000000);
		System.out.println("Time taken: " + (fullDuration - duration) + "sec");
		duration = fullDuration;
		
		
		System.out.println("\nRun over!");
		
		//FlowShop.drawDiagram(generation.get(0));
		//FlowShop.drawMatrix(generation.get(0));

		
	}
	
	public static ArrayList<int[][]> runOnegeneration(int[][] base) {		
		ArrayList<int[][]> generation = generateGeneration(base);
		int numofgenerations = 1000;
		int generationsize = 100;
		System.out.println("\n\n");
		System.out.println("J: " + base.length + " M: " + (base[0].length-1) + "\n(with " + numofgenerations + ". generations, " + generationsize + " element/generation)");
		
		
		for (int i=0; i<numofgenerations; i++) {
			generation = fillGeneration(generation, generationsize);				
			callRecombination(generation);
			
			int[] cmax = getCMax(generation);	
			
			
			generatePhenotype(generation, cmax);
					
			
			/*
			//cmax = getCMax(generation);	
			System.out.print((i+1) + ":  ");
			
			for (int k=0; k<cmax.length; k++) {
				System.out.print(cmax[k] + ", ");
			}
			
			System.out.print("\n");
			*/
			
			
			//generation = extinctionConstant(generation);
			generation = extinctionSecondHalf(generation);
						
		}
		System.out.println("C max = " + FlowShop.calculateResultArray(FlowShop.generateResultArray(generation.get(0)[0].length-1), generation.get(0))[generation.get(0)[0].length-2]);
		return generation;
	}
	
		
	
	public static ArrayList<int[][]> generateGeneration(int[][] base) {
		ArrayList<int[][]> generation = new ArrayList<int[][]> ();
		//a növekvõ és csökkenõ sorrend mindig tagja a generációnak
		generation.add(base);
		int[][] reverse = base.clone();
		for (int i = 0; i < base.length; i++) {			
			reverse[i]=base[base.length-i-1];			
		}
		generation.add(reverse);
		return generation;
	}
	
	public static ArrayList<int[][]> fillGeneration(ArrayList<int[][]> generation, int count) {
		for (int j=generation.size()-1; j<count-1;j++) {
			
			int whichMutate = (int) (Math.random() * 2 );
			
			int[][] temp = generation.get(0).clone();
			for (int i=0; i<temp.length; i++) {
				if (whichMutate==0) {
					mutateByReplace(temp);
				}
				else {
					mutateByBackwards(temp);
				}
				
			}
			generation.add(temp);
		}
		return generation;
	}
	
	
	//Mutáció - két J kicserélésére egy mátrixban
	public static void mutateByReplace( int[][] data) {
		int[] temp;
		int a = (int) (Math.random() * data.length );
		int b = 0;
		do {
			b = (int) (Math.random() * data.length );
		} while (a==b);		 
		
		temp = data[a];
		data[a]=data[b];
		data[b]=temp;				
	}
	
	//Mutáció - egy szakasz megfordítása a mátrixban
		public static void mutateByBackwards( int[][] data) {			
			int cutSize = (int) (Math.random() * (data.length/2-1) + 2);			
			int cut = (int) (Math.random() * (data.length-cutSize+1));
			int[][] temp = new int[cutSize][data[0].length];
			
			for (int i=0; i<(cutSize/2); i++) {
				temp[i]=data[cut+i];
				data[cut+i]=data[cutSize+cut-i-1];
				data[cutSize+cut-i-1]=temp[i];
			}
					
		}
	
	
	//Rekombináció - egy random Jmaxnál kissebb méretû szeletet cserélünk ki két mátrix véletlenszerûen választott pontján	
	public static void recombination(int[][] first, int[][] second) {
		int firstCut, secondCut, cutSize;
		cutSize = (int) (Math.random() * (first.length-1) + 1);
		
		firstCut = (int) (Math.random() * (first.length-cutSize+1));
		secondCut = (int) (Math.random() * (first.length-cutSize));

		int[][] tempSecond = second.clone();		
		int[] temp;
		
		for (int i=0; i<cutSize; i++) {
			
			for (int j=0; j<first[0].length;j++) {
				if (second[j][0] == first[firstCut+i][0]) {
					temp = second[j];
					second[j]=second[secondCut+i];
					second[secondCut+i]=temp;
				}				
			}
			
			for (int j=0; j<first[0].length;j++) {
				if (first[j][0] == tempSecond[secondCut+i][0]) {
					temp = first[j];
					first[j]=first[firstCut+i];
					first[firstCut+i]=temp;
				}				
			}
		}
		
	}
	
	public static ArrayList<int[][]> callRecombination(ArrayList<int[][]> generation) {
		int second = 0;
		//az elsõ elemen nem hajtjuk végre
		for (int i=1; i<generation.size(); i++) {
			do {
				second = (int) (Math.random() * (generation.size()-1) +1);
			} while (second == 0);

			
			recombination(generation.get(i),generation.get(second));
		}
			
		return generation;
	}
	
	public static int[] getCMax(ArrayList<int[][]> generation) {
		int[] cmax = new int[generation.size()];
		for (int i=0; i<generation.size();i++) {
			cmax[i]=FlowShop.calculateResultArray(
					FlowShop.generateResultArray(generation.get(0)[0].length-1)
					, generation.get(i))
					[generation.get(0)[0].length-2];
		}
		return cmax;
	}
	
	public static void generatePhenotype(ArrayList<int[][]> generation, int[] cmin) {
		
		for (int j=0; j<cmin.length;j++) {
			int min=cmin[j];
			int minWhere=j;
			for (int i=j; i<cmin.length;i++) {				
				if (cmin[i]<min) {
					int[][] temp = generation.get(minWhere).clone();
					int cTemp = cmin[minWhere];
					
					min=cmin[i];
					cmin[minWhere]=min;
					cmin[i]=cTemp;
							
					generation.set(minWhere, generation.get(i));
					generation.set(i, temp);
				}				
			}
		}
	}

	//kihalás csak második felének
	public static ArrayList<int[][]> extinctionSecondHalf(ArrayList<int[][]> generation){	
		int size=0;
		if (generation.size()%2==0) {
			size=generation.size()/2;
		}
		else {
			size=(generation.size()-1)/2;
		}
			
		ArrayList<int[][]> survivors = new ArrayList<int[][]> (size);
			
		for (int i=0; i< size; i++) {
			survivors.add(generation.get(i));
		}
			
		return survivors;
	}
		
		
	//konstans alapján kihalás
	public static ArrayList<int[][]> extinctionConstant(ArrayList<int[][]> generation){		
		ArrayList<int[][]> survivors = new ArrayList<int[][]> ();
		//a legjobb mindig túléli
		survivors.add(generation.get(0));
			
		double constant;
		for (int i=1; i<generation.size();i++) {
			constant=0.8;
			if (i>1) {
				constant=Math.pow(1-constant,i-1);
			}			
			int surviveTemp=(int) (Math.random() * 100);
			double survive =(double)surviveTemp/100;
				
			//System.out.println("Nope" + i +". : "+survive + " < " + constant);
			if (survive < constant) {
				//System.out.println("Survived" + i +". : "+survive + " < " + constant);
				survivors.add(generation.get(i));
			}
		}

		return survivors;
	}	
	
	

}