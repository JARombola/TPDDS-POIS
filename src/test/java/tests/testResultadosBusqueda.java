package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

public class testResultadosBusqueda {

	@Test
	public void test() {
		new LocalDate();
		System.out.println(LocalDate.now());
		List<Integer>asd=new ArrayList<Integer>();
		asd.addAll(Arrays.asList(1,2,3,4,5,6));
		int asd2= asd.stream().filter(a->a>3).findFirst().orElse(-1);
		if (asd2!=-1){
		System.out.println(asd2);}
		else {System.out.println("Vacio");}
	}

}
