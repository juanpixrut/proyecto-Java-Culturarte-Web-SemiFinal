package test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Juanpi
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import logica.*;
import persistencia.*;
import logica.dtos.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;

public class ControladoraTest {

    private fabrica fab = new fabrica();
    private ictrl ic = fab.getIctrl();

    // Creamos una subclase para poder inyectar datos falsos
    class ControladoraMock extends ictrl {

        private List<proponente> proponentes = new ArrayList<>();
        private List<colaborador> colaboradores = new ArrayList<>();
        private List<propuesta> propuestas = new ArrayList<>();
        private List<colaboracion> colaboraciones = new ArrayList<>();

        ControladoraPersistenciaMock persistenciaMock = new ControladoraPersistenciaMock();

        @Override
        public String buscoRol(String nickname) {
            for (proponente p : listarProponentes()) {
                if (p != null && nickname.equalsIgnoreCase(p.getNickname())) {
                    return "proponente";
                }
            }
            for (colaborador c : listarColaboradores()) {
                if (c != null && nickname.equalsIgnoreCase(c.getNickname())) {
                    return "colaborador";
                }
            }
            return null;
        }

        @Override
        public void altaPerfilProponente(String nickname, String nombre, String apellido, String correo, byte[] imagenBytes, String direccion, String biografia, String linkSitio, String contrasena) {
            proponente p = new proponente(nickname, nombre, apellido, correo, imagenBytes, contrasena, direccion, biografia, linkSitio);
            persistenciaMock.crearUsuarioProponente(p);
        }

        @Override
        public void altaPerfilColaborador(String nickname, String nombre, String apellido, String correo, byte[] imagenBytes) {
            colaborador c = new colaborador(nickname, nombre, apellido, correo, imagenBytes);
            persistenciaMock.crearUsuarioColaborador(c);
        }

        @Override
        public List<proponente> listarProponentes() {
            return proponentes;
        }

        @Override
        public List<colaborador> listarColaboradores() {
            return colaboradores;
        }

        @Override
        public List<propuesta> listarPropuestas() {
            return propuestas;
        }

        @Override
        public List<colaboracion> listarColaboraciones() {
            return colaboraciones;
        }

        public void agregarProponente(proponente p) {
            proponentes.add(p);
        }

        public void agregarColaborador(colaborador c) {
            colaboradores.add(c);
        }

        @Override
        public proponente buscoProponente(String nickname) {
            for (proponente p : this.listarProponentes()) {
                if (p.getNickname().equalsIgnoreCase(nickname)) {
                    return p;
                }
            }
            return null;
        }

        @Override
        public colaborador buscoColaborador(String nickname) {
            for (colaborador c : this.listarColaboradores()) {
                if (c.getNickname().equalsIgnoreCase(nickname)) {
                    return c;
                }
            }
            return null;
        }

        @Override
        public usuario buscoUsuario(String nickname) {
            colaborador col = mockCtrl.buscoColaborador(nickname);
            proponente prop = mockCtrl.buscoProponente(nickname);

            if (col != null) {
                usuario user = col;
                return user;
            }
            if (prop != null) {
                usuario user = prop;
                return user;
            }
            return null;
        }

        public void agregarPropuesta(propuesta prop) {
            propuestas.add(prop);
        }

        //public propuesta buscoPropuesta(String titulo) {
        //    for (propuesta p : propuestas) {
        //        if(p.getTitulo().equalsIgnoreCase(titulo)){
        //        return p;
        //        }
        //    }
        //   return null;
        //}
        public void agregarColaboracion(colaboracion col) {
            colaboraciones.add(col);
        }

        public colaboracion buscoColaboracion(int id) {
            for (colaboracion c : colaboraciones) {
                if (c.getId() == 10) {
                    return c;
                }
            }
            return null;
        }

        @Override
        public List<PropuestaDTO> listarPropuestasFavoritas(String nickname) {
            String rol = buscoRol(nickname);
            usuario u = null;

            if ("proponente".equalsIgnoreCase(rol)) {
                u = buscoProponente(nickname);
            } else if ("colaborador".equalsIgnoreCase(rol)) {
                u = buscoColaborador(nickname);
            }

            List<PropuestaDTO> resultado = new ArrayList<>();

            if (u != null && u.getFavoritas() != null) {
                for (propuesta p : u.getFavoritas()) {
                    if (p != null) {
                        resultado.add(PropuestaDTO.fromEntity(p));
                    }
                }
            }

            return resultado;
        }

    }

    // mock del controlador de persistencia
    class ControladoraPersistenciaMock extends ControladoraPersistencia {

        public boolean llamado = false;
        public proponente ultimoProponente = null;
        public colaborador ultimoColaborador = null;

        @Override
        public void crearUsuarioProponente(proponente p) {
            llamado = true;
            ultimoProponente = p;
        }

        @Override
        public void crearUsuarioColaborador(colaborador c) {
            llamado = true;
            ultimoColaborador = c;
        }

    }

    private ControladoraMock mockCtrl;

    @Before
    public void setUp() {
        mockCtrl = new ControladoraMock();
    }

    @Test
    public void validarUsuarioExistenteYClaveCorrecta() {
        proponente p = new proponente();
        p.setNickname("juanpi");
        p.setContrasena("1234");
        mockCtrl.agregarProponente(p);

        boolean resultado = mockCtrl.validarUsuario("juanpi", "1234");
        assertTrue("El usuario debería ser válido con clave correcta", resultado);
    }

    @Test
    public void validarUsuarioExistenteYClaveIncorrecta() {
        proponente p = new proponente();
        p.setNickname("juanpi");
        p.setContrasena("1234");
        mockCtrl.agregarProponente(p);

        boolean resultado = mockCtrl.validarUsuario("juanpi", "0000");
        assertFalse("El usuario no debería ser válido con clave incorrecta", resultado);
    }

    @Test
    public void validarUsuarioInexistente() {
        proponente p = new proponente();
        p.setNickname("flor");
        p.setContrasena("abcd");
        mockCtrl.agregarProponente(p);

        boolean resultado = mockCtrl.validarUsuario("juanpi", "abcd");
        assertFalse("El usuario no debería ser válido si no existe", resultado);
    }

    @Test
    public void buscoRol() {
        proponente p = new proponente();
        p.setNickname("mario");
        mockCtrl.agregarProponente(p);

        colaborador c = new colaborador();
        c.setNickname("ana");
        mockCtrl.agregarColaborador(c);

        assertEquals("proponente", mockCtrl.buscoRol("mario"));
        assertEquals("colaborador", mockCtrl.buscoRol("ana"));
        assertNull(mockCtrl.buscoRol("inexistente"));
    }

    @Test
    public void buscoProponente() {
        proponente p = new proponente();
        p.setNickname("mario");
        mockCtrl.agregarProponente(p);

        assertEquals(p, mockCtrl.buscoProponente("mario"));
    }

    @Test
    public void buscoColaborador() {
        colaborador c = new colaborador();
        c.setNickname("mario");
        mockCtrl.agregarColaborador(c);

        assertEquals(c, mockCtrl.buscoColaborador("mario"));
    }

    @Test
    public void buscoUsuario() {
        proponente p = new proponente();
        p.setNickname("mario");
        mockCtrl.agregarProponente(p);
        usuario user = p;

        assertEquals(user, mockCtrl.buscoUsuario("mario"));
    }

    @Test
    public void altaPropuesta() {
        proponente p = new proponente();
        p.setNickname("mario");
        propuesta prop = new propuesta(p, "prueba", null, null, null, null, 10, 10, null);
        mockCtrl.agregarPropuesta(prop);

        assertEquals(prop, mockCtrl.buscoPropuesta("prueba"));
    }

    @Test
    public void altaColaboracion() {
        colaborador c = new colaborador();
        c.setNickname("mario");

        proponente p = new proponente();
        p.setNickname("mario");
        propuesta prop = new propuesta(p, "prueba", null, null, null, null, 10, 10, null);

        colaboracion col = new colaboracion(c, prop, 10, null);
        col.setId(10);
        mockCtrl.agregarColaboracion(col);

        assertEquals(col, mockCtrl.buscoColaboracion(10));

    }

    @Test
    public void testBuscoPropuesta_ExisteExacta() {
        // preparamos los datos simulados
        propuesta p1 = new propuesta();
        p1.setTitulo("titulo p1");
        propuesta p2 = new propuesta();
        p2.setTitulo("titulo p2");

        mockCtrl.agregarPropuesta(p1);
        mockCtrl.agregarPropuesta(p2);

        // ejecutamos el método
        propuesta resultado = mockCtrl.buscoPropuesta("titulo p1");

        // verificamos que encontró la correcta
        assertNotNull("Debe encontrar la propuesta con ese título", resultado);
        assertEquals("titulo p1", resultado.getTitulo());
    }

    @Test
    public void testBuscoPropuesta_Inexistente() {
        propuesta p = new propuesta();
        p.setTitulo("titulo p");
        mockCtrl.agregarPropuesta(p);

        propuesta resultado = mockCtrl.buscoPropuesta("titulo erroneo");

        assertNull("Debe devolver null si no encuentra la propuesta", resultado);
    }

    @Test
    public void testBuscoColaborador_ExisteExacto() {
        colaborador c1 = new colaborador();
        c1.setNickname("juanpi");
        colaborador c2 = new colaborador();
        c2.setNickname("flor");

        mockCtrl.agregarColaborador(c1);
        mockCtrl.agregarColaborador(c2);

        colaborador resultado = mockCtrl.buscoColaborador("flor");

        assertNotNull("Debe encontrar al colaborador con ese nickname", resultado);
        assertEquals("flor", resultado.getNickname());
    }

    @Test
    public void testBuscoColaborador_Inexistente() {
        colaborador c = new colaborador();
        c.setNickname("ana");
        mockCtrl.agregarColaborador(c);

        colaborador resultado = mockCtrl.buscoColaborador("pepe");
        assertNull("Debe devolver null si no existe el colaborador", resultado);
    }

    @Test
    public void testAltaPerfilProponente_LlamaAPersistenciaYGuardaDatos() {
        byte[] imagen = new byte[]{1, 2, 3};

        mockCtrl.altaPerfilProponente(
                "juanpi",
                "Juan",
                "fontes",
                "juan@gmail.com",
                imagen,
                "Calle 123",
                "Programador",
                "www.juan.com",
                "1234"
        );

        ControladoraPersistenciaMock persistMock = mockCtrl.persistenciaMock;

        assertTrue("Debe haberse llamado a crearUsuarioProponente()", persistMock.llamado);
        assertNotNull("Debe haberse pasado un proponente al método", persistMock.ultimoProponente);
        assertEquals("juanpi", persistMock.ultimoProponente.getNickname());
        assertEquals("juan@gmail.com", persistMock.ultimoProponente.getEmail());
        assertEquals("Juan", persistMock.ultimoProponente.getNombre());
        assertEquals("fontes", persistMock.ultimoProponente.getApellido());
        assertArrayEquals(imagen, persistMock.ultimoProponente.getImagen());
    }

    @Test
    public void testAltaPerfilColaborador_LlamaAPersistenciaYGuardaDatos() {
        byte[] imagen = new byte[]{9, 8, 7};

        mockCtrl.altaPerfilColaborador(
                "flor",
                "Florencia",
                "apellido",
                "flor@gmail.com",
                imagen
        );

        ControladoraPersistenciaMock persistMock = mockCtrl.persistenciaMock;

        assertTrue("Debe haberse llamado a crearUsuarioColaborador()", persistMock.llamado);
        assertNotNull("Debe haberse pasado un colaborador al método", persistMock.ultimoColaborador);
        assertEquals("flor", persistMock.ultimoColaborador.getNickname());
        assertEquals("Florencia", persistMock.ultimoColaborador.getNombre());
        assertEquals("apellido", persistMock.ultimoColaborador.getApellido());
        assertEquals("flor@gmail.com", persistMock.ultimoColaborador.getEmail());
        assertArrayEquals(imagen, persistMock.ultimoColaborador.getImagen());
    }

    @Test
    public void testListarPropuestasFavoritas_DeProponente() {
        // Creamos un proponente con una propuesta favorita
        proponente p = new proponente();
        p.setNickname("juanpi");

        propuesta prop = new propuesta();
        prop.setTitulo("Festival de Música");
        prop.setDescripcion("Evento con bandas locales.");
        prop.setLugar("Montevideo");
        prop.setMonto(5000);
        prop.setProponente(p);
        prop.setEstado(estadoPropuesta.INGRESADA);

        // Simulamos lista de favoritas
        List<propuesta> favoritas = new ArrayList<>();
        favoritas.add(prop);
        p.setFavoritas(favoritas);

        mockCtrl.agregarProponente(p);

        // Ejecutamos
        List<PropuestaDTO> resultado = mockCtrl.listarPropuestasFavoritas("juanpi");

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        PropuestaDTO dto = resultado.get(0);
        assertEquals("Festival de Música", dto.getTitulo());
        assertEquals("Montevideo", dto.getLugar());
        assertEquals(5000, dto.getMontoNecesario(), 0.01);
    }

    @Test
    public void testListarPropuestasPublicadas_DeProponente() {
        // 1. Creamos el proponente
        proponente p = new proponente();
        p.setNickname("juanpi");
        mockCtrl.agregarProponente(p);

        // 2. Mockeamos las propuestas DTO
        PropuestaDTO propPublicada = new PropuestaDTO();
        propPublicada.setTitulo("Obra de Teatro");
        propPublicada.setProponenteNickname("juanpi");
        propPublicada.setEstadoActual(EstadoPropuestaDTO.PUBLICADA);

        PropuestaDTO propNoPublicada = new PropuestaDTO();
        propNoPublicada.setTitulo("Concierto");
        propNoPublicada.setProponenteNickname("juanpi");
        propNoPublicada.setEstadoActual(EstadoPropuestaDTO.INGRESADA);

        // 3. Sobrescribimos listarPropuestasDTO() en el mock
        mockCtrl = new ControladoraMock() {
            @Override
            public List<PropuestaDTO> listarPropuestasDTO() {
                List<PropuestaDTO> lista = new ArrayList<>();
                lista.add(propPublicada);
                lista.add(propNoPublicada);
                return lista;
            }

            @Override
            public String buscoRol(String nickname) {
                return "proponente";
            }

            @Override
            public proponente buscoProponente(String nickname) {
                return p;
            }
        };

        // 4. Ejecutamos el método
        List<PropuestaDTO> resultado = mockCtrl.listarPropuestasPublicadas("juanpi");

        // 5. Verificamos resultados
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Obra de Teatro", resultado.get(0).getTitulo());
        assertEquals(EstadoPropuestaDTO.PUBLICADA, resultado.get(0).getEstadoActual());
    }


}
