package za.co.discovery.controllers;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class RootControllerTest {
    private MockHttpSession session;
    private MockMvc mockMvc;

//    @Test
//    public void testHome() throws Exception {
//        setUpFixture();
////        session.setAttribute("de","de");
//        List<Vertex> newVertexList = new ArrayList<>();
//        newVertexList.add(aVertex().build());
//        mockMvc.perform(get("/index"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"))
//                .andExpect(model().attribute("vertexList", equalTo(newVertexList)));
//    }
//
//    @Test
//    public void testUpdatePage() throws Exception {
//        setUpFixture();
//        List<Vertex> newVertexList = new ArrayList<>();
//        newVertexList.add(aVertex().build());
//        mockMvc.perform(get("/update"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("update"))
//                .andExpect(model().attribute("vertexList", equalTo(newVertexList)));
//    }
//
//    @Test
//    public void testDeletePage() throws Exception {
//        setUpFixture();
//        mockMvc.perform(get("/delete"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("delete"));
//    }
//


//    @Test
//    public void testPathPrintedOutIsCorrect() throws Exception{
//
//        String destination = "B";
//        mockMvc.perform(get("/deleteVertex").session(session)
//                .param("path", String.valueOf(destination))
//        );
//        assertThat();
//    }

    public void setUpFixture() {
        mockMvc = standaloneSetup(
                new RootController(null
                        , null, null
                ) {
                })
                .setViewResolvers(getInternalResourceViewResolver())
                .build();
    }

    private InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
}