package co.gov.banrep.neteo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import co.gov.banrep.neteo.algoritmos.MatrizUtils;
import co.gov.banrep.neteo.algoritmos.Multilateral;

@SpringBootTest
class MultilateralSimulationApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void probarMultilateral() {
		int [][] matriz = new int[][] {{0,0,3,0,4,5,6,7},{0,0,0,2,0,0,3,1},{0,1,0,0,6,7,9,1},{3,0,0,0,5,6,3,0},{0,0,3,0,0,5,6,7},{0,3,1,2,0,0,3,1},{0,1,0,8,6,7,0,1},{3,0,0,0,5,6,3,0}};
		List<Integer>camino = Multilateral.encontrarCamino(matriz, 5);
		MatrizUtils.obtenerCamino(camino);
	}

}
