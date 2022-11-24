package pack;

public class FlowShop {	
	
	public static int[][] generateRandomData(int j, int m) {
		int random;		
		
		int[][] data = new int[j][m+1];
		for (int i = 0; i < j; i++) {
			
			int[] aldata = new int[m+1];
			aldata[0]=i+1;
			
			for (int k = 1; k < m+1; k++) {
				random = (int) (Math.random() * 99 +1);
				aldata[k]=random;
			}
			
			data[i]=aldata;
		}
		
		return data;
	}	
	
	public static int[] generateResultArray(int m) {
		
		int[] result = new int[m];
		for (int i = 0; i < m; i++) {
			result[i]=0;
		}			
		return result;
	}

	public static int[] calculateResultArray(int[] result, int[][] data) {
		result[0]=data[0][1];
		
		for (int resultCount = 1; resultCount < result.length; resultCount++) {	

			result[resultCount] = result[resultCount-1]+data[0]
					[resultCount+1];
		}
		
		for (int dataRow = 1; dataRow  < data.length; dataRow ++) {	

			result[0] = result[0]+data[dataRow][1];

			for (int dataColumn = 1; dataColumn < data[dataRow].length-1; dataColumn++) {				
				
				result[dataColumn] = 
						Math.max(result[dataColumn-1]+data[dataRow][dataColumn+1],
								result[dataColumn]+data[dataRow][dataColumn+1]);
			}			
		}

		return result;
	}
	
	public static void drawMatrixAsNormalData(int[][] data) {
		
		for (int dataRow = 0; dataRow  < data.length; dataRow++) {		
			System.out.print("\n{");	
			for (int dataColumn = 1; dataColumn < data[0].length-1; dataColumn++) {	
		
				System.out.print(" "+data[dataRow][dataColumn]+",");
				
				if (dataColumn == data[0].length-2) {
					System.out.print(" "+data[dataRow][dataColumn+1]+"");
				}
				
			}
			System.out.print("},");	
			
		}
		System.out.println("\n");
	}
	
	public static void drawMatrix(int[][] data) {
		System.out.print("  ");
		for (int i = 1; i  < data[0].length; i++) {				
			if (i<10) {
				System.out.print(" ");
			}		
			
			System.out.print("  M"+i);				
			
		}		
		
		for (int dataRow = 0; dataRow  < data.length; dataRow++) {				
			System.out.print("\nJ"+(data[dataRow][0]));	
			
			if (data[dataRow][0]<10) {
				System.out.print(" ");
			}
			if (data[dataRow][0]<100) {
				System.out.print(" ");
			}			
			
			for (int dataColumn = 1; dataColumn < data[0].length; dataColumn++) {	
				if (data[dataRow][dataColumn]<10) {
					System.out.print(" ");
				}			
				System.out.print(" "+data[dataRow][dataColumn]+"  ");
			}				
		}
		System.out.println("\n");
	}
	
	
	public static void drawDiagram(int[][] matrix) {
		int[][] line = new int[matrix.length][matrix[0].length];
		line[0][0]=0;
		
		System.out.println("\n");
		
		int whatToWrite = 1;
		
		for (int m=1; m < matrix[0].length; m++) {
			for (int j=0; j < matrix.length; j++) {
				
				if (m==1 && j==0) {
					line[j][m]=matrix[j][m];
				}
				
				if (m==1 && j>0) {			
					line[j][m]=line[j-1][m]+matrix[j][m];
				}
				
				if (m>1 && j==0) {	
					for (int h=0; h < line[j][m-1]; h++) {
						System.out.print("-");
					}
					line[j][m]=line[j][m-1]+matrix[j][m];					
				}
				
				if (m>1 && j>0) {			
					if (line[j][m-1]>line[j-1][m]) {
						line[j][m]=line[j][m-1]+matrix[j][m];
						for (int h=line[j-1][m]; h < line[j][m-1]; h++) {
							System.out.print("-");
						}
					}
					else {
						line[j][m]=line[j-1][m]+matrix[j][m];
					}					
				}
				
				for (int k=0; k < matrix[j][m]; k++) {
					if (whatToWrite >9) {
						whatToWrite=1;
					}
					
					System.out.print(whatToWrite);				
				}	
				whatToWrite++;
			}
			System.out.println(" ");
		}
	}		
	
	
}
