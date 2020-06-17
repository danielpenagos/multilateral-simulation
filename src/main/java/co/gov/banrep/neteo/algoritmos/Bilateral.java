package co.gov.banrep.neteo.algoritmos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bilateral {
	
	static Logger logger = LogManager.getLogger(Bilateral.class);
	public static int[][]  netear(int[][] entrada) {
		
		int[][]matriz=new int[entrada.length][];
		for(int i=0;i<entrada.length;i++) {
			matriz[i]=entrada[i].clone();
		}
			
		
		int numEntidades = matriz.length;
		for(int i =0; i<numEntidades-1;i++) {
			for(int j =i; j<numEntidades;j++) {
				if(matriz[i][j]>matriz[j][i]) {
					matriz[i][j]-=matriz[j][i];
					matriz[j][i]=0;
				}else {
					matriz[j][i]-=matriz[i][j];
					matriz[i][j]=0;
				}
			}
		}
		logger.debug("matriz bilateral:");
		MatrizUtils.imprimir(matriz);
		return matriz;		
	}

}
