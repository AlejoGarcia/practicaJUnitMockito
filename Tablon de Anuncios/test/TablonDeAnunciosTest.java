import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;



public class TablonDeAnunciosTest {
	
		private TablonDeAnuncios ta;
		
		@Before
		public void setUp(){
			ta = new TablonDeAnuncios();
		}
		
		@Test
		public void Test1(){
			assertEquals(1,ta.anunciosPublicados());
		}
		
		@Test
		public void Test2(){
			Anuncio an = new Anuncio("Anuncio","","LA EMPRESA");
			ta.publicarAnuncio(an, null, null);
			assertEquals(2,ta.anunciosPublicados());
		}
		
		@Test
		public void Test3(){
			IBaseDeDatosDeAnunciantes bda = mock(IBaseDeDatosDeAnunciantes.class);
			IBaseDeDatosDePagos bdp = mock(IBaseDeDatosDePagos.class);
			when(bda.buscarAnunciante("empresa AUX")).thenReturn(true);
			when(bdp.anuncianteTieneSaldo("empresa AUX")).thenReturn(false);
			Anuncio an = new Anuncio("Anuncio", "", "empresa AUX");
			ta.publicarAnuncio(an, bda, bdp);
			assertEquals(null , ta.buscarAnuncioPorTitulo("Anuncio"));
			assertEquals(1, ta.anunciosPublicados());
		}
		
		@Test
		public void Test4(){
			IBaseDeDatosDeAnunciantes bda = mock(IBaseDeDatosDeAnunciantes.class);
			IBaseDeDatosDePagos bdp = mock(IBaseDeDatosDePagos.class);
			when(bda.buscarAnunciante("empresa AUX")).thenReturn(true);
			when(bdp.anuncianteTieneSaldo("empresa AUX")).thenReturn(true);
			Anuncio an = new Anuncio("Anuncio", "", "empresa AUX");
			ta.publicarAnuncio(an, bda, bdp);
			assertEquals(an , ta.buscarAnuncioPorTitulo("Anuncio"));
			assertEquals(2, ta.anunciosPublicados());
			verify(bdp).anuncioPublicado("empresa AUX");
		}

		@Test
		public void Test5(){
			Anuncio an1 = new Anuncio("Anuncio1", "", "LA EMPRESA");
			Anuncio an2 = new Anuncio("Anuncio2", "", "LA EMPRESA");
			ta.publicarAnuncio(an1, null, null);
			ta.publicarAnuncio(an2, null, null);
			assertEquals(an2, ta.buscarAnuncioPorTitulo("Anuncio2"));
			assertEquals(3, ta.anunciosPublicados());
		}
		
		@Test
		public void Test6(){
			Anuncio an1 = new Anuncio("Anuncio1", "", "LA EMPRESA");
			Anuncio an2 = new Anuncio("Anuncio2", "", "LA EMPRESA");
			ta.publicarAnuncio(an1, null, null);
			ta.publicarAnuncio(an2, null, null);
			ta.borrarAnuncio("Anuncio1", "LA EMPRESA");
			assertEquals(null, ta.buscarAnuncioPorTitulo("Anuncio1"));
			assertEquals(2, ta.anunciosPublicados());
		}
		
		
		@Test
		public void Test7(){
			IBaseDeDatosDeAnunciantes bda = mock(IBaseDeDatosDeAnunciantes.class);
			IBaseDeDatosDePagos bdp = mock(IBaseDeDatosDePagos.class);
			when(bda.buscarAnunciante("empresa AUX")).thenReturn(true);
			when(bdp.anuncianteTieneSaldo("empresa AUX")).thenReturn(true);
			
			Anuncio an1 = new Anuncio("Anuncio1", "UNO", "empresa AUX");
			Anuncio an2 = new Anuncio("Anuncio1", "DOS", "empresa AUX");
			ta.publicarAnuncio(an1, bda, bdp);
			assertEquals(2, ta.anunciosPublicados());
			ta.publicarAnuncio(an2, bda, bdp);
			assertEquals(2, ta.anunciosPublicados());
			assertEquals(an1,ta.buscarAnuncioPorTitulo("Anuncio1"));
			assertNotEquals(an2,ta.buscarAnuncioPorTitulo("Anuncio1"));
			
			
			an1 = new Anuncio("Anuncio2", "TRES", "LA EMPRESA");
			an2 = new Anuncio("Anuncio2", "CUATRO", "LA EMPRESA");
			ta.publicarAnuncio(an1, null, null);
			assertEquals(3, ta.anunciosPublicados());
			ta.publicarAnuncio(an2, null, null);
			assertEquals(3, ta.anunciosPublicados());
			assertEquals(an1,ta.buscarAnuncioPorTitulo("Anuncio2"));
			assertNotEquals(an2,ta.buscarAnuncioPorTitulo("Anuncio2"));
		}
		
		@Test(expected = AnuncianteNoExisteException.class)
		public void Test8(){
			IBaseDeDatosDeAnunciantes bda = mock(IBaseDeDatosDeAnunciantes.class);
			IBaseDeDatosDePagos bdp = mock(IBaseDeDatosDePagos.class);
			when(bda.buscarAnunciante("empresa AUX")).thenReturn(false);
			Anuncio an = new Anuncio("Anuncio", "", "empresa AUX");
			ta.publicarAnuncio(an, bda, bdp);
		}
}
