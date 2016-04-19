package za.co.discovery.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Traffic;
import za.co.discovery.models.Vertex;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.TrafficService;
import za.co.discovery.services.VerticesService;

import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static za.co.discovery.models.EdgeBuilder.anEdge;
import static za.co.discovery.models.TrafficBuilder.aTraffic;
import static za.co.discovery.models.VertexBuilder.aVertex;

@RunWith(MockitoJUnitRunner.class)
public class RootControllerTest {
    private MockMvc mockMvc;

    @Mock
    private VerticesService verticesService;
    @Mock
    private EdgesService edgeService;
    @Mock
    private TrafficService trafficService;

    @Test
    public void testHome() throws Exception {
        List<Vertex> newVertexList = singletonList(aVertex().build());
        Traffic traffic1 = aTraffic().withRouteId(1).withSource("A").withDestination("B").withDistance(4).build();
        Edge edge1 = anEdge().withRouteId(1).withSource("A").withDestination("B").withDistance(4).build();

        when(verticesService.getVertexList()).thenReturn(newVertexList);
        when(edgeService.getEdgeList()).thenReturn(singletonList(edge1));
        when(trafficService.getTrafficList()).thenReturn(singletonList(traffic1));
        setUpFixture();
        Traffic expectedTraffic = aTraffic().withRouteId(1).withSource("A").withDestination("B").withDistance(8).build();

        mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("vertexList", equalTo(newVertexList)));
        ArgumentCaptor<Traffic> trafficArgument = forClass(Traffic.class);
        verify(trafficService).updateTraffic(trafficArgument.capture());
        assertThat(trafficArgument.getValue(), is(sameBeanAs(expectedTraffic)));

    }

    @Test
    public void testUpdatePage() throws Exception {
        List<Vertex> newVertexList = singletonList(aVertex().build());
        List<Edge> newEdgeList = singletonList(anEdge().build());
        List<Traffic> newTrafficList = singletonList(aTraffic().build());
        when(verticesService.getVertexList()).thenReturn(newVertexList);
        when(edgeService.getEdgeList()).thenReturn(newEdgeList);
        when(trafficService.getTrafficList()).thenReturn(newTrafficList);
        setUpFixture();
        mockMvc.perform(get("/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attribute("edgeList", equalTo(newEdgeList)))
                .andExpect(model().attribute("trafficList", equalTo(newTrafficList)))
                .andExpect(model().attribute("vertexList", equalTo(newVertexList)));
    }

    @Test
    public void testAddNodePage() throws Exception {
        List<Vertex> newVertexList = singletonList(aVertex().build());
        when(verticesService.getVertexList()).thenReturn(newVertexList);
        setUpFixture();
        mockMvc.perform(get("/addNode"))
                .andExpect(status().isOk())
                .andExpect(view().name("addNode"))
                .andExpect(model().attribute("vertexList", equalTo(newVertexList)));
    }

    @Test
    public void testDeletePage() throws Exception {
        List<Vertex> newVertexList = singletonList(aVertex().build());
        when(verticesService.getVertexList()).thenReturn(newVertexList);
        List<Edge> newEdgeList = singletonList(anEdge().build());
        when(edgeService.getEdgeList()).thenReturn(newEdgeList);
        setUpFixture();
        mockMvc.perform(get("/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("delete"))
                .andExpect(model().attribute("edgeList", equalTo(newEdgeList)))
                .andExpect(model().attribute("vertexList", equalTo(newVertexList)));
    }

    @Test
    public void testVertexGetsDeleted() throws Exception {
        setUpFixture();
        when(verticesService.deleteVertex("A")).thenReturn(1);
        mockMvc.perform(get("/deleteVertex/A"));
    }

    @Test
    public void testEdgeGetsDeleted() throws Exception {
        setUpFixture();
        when(edgeService.deleteEdge(1)).thenReturn(1);
        mockMvc.perform(get("/deleteRoute/1"));
    }

    @Test
    public void testVertexGetsUpdated() throws Exception {
        Vertex expectedVertex = aVertex().withNode("A").withPlanetName("newEarth").build();
        setUpFixture();
        mockMvc.perform(get("/updateVertex/A,newEarth"));
        ArgumentCaptor<Vertex> vertexArgument = forClass(Vertex.class);
        verify(verticesService).updateVertex(vertexArgument.capture());
        assertThat(vertexArgument.getValue(), is(sameBeanAs(expectedVertex)));
    }

    @Test
    public void testTrafficGetsUpdated() throws Exception {
        Traffic expectedTraffic = aTraffic()
                .withRouteId(1)
                .withSource("A")
                .withDestination("B")
                .withDistance(2)
                .build();
        setUpFixture();
        when(trafficService.getTrafficById(1)).thenReturn(expectedTraffic);

        mockMvc.perform(get("/updateTraffic/1,2"));
        ArgumentCaptor<Traffic> trafficArgument = forClass(Traffic.class);
        verify(trafficService).updateTraffic(trafficArgument.capture());
        assertThat(trafficArgument.getValue(), is(sameBeanAs(expectedTraffic)));
    }

    @Test
    public void testEdgeGetsUpdated() throws Exception {
        Edge expectedEdge = anEdge().withRouteId(1).withSource("A").withDestination("B").withDistance(4).build();
        setUpFixture();
        mockMvc.perform(get("/updateRoute/1,A,B,4"));
        ArgumentCaptor<Edge> edgeArgument = forClass(Edge.class);
        verify(edgeService).updateEdge(edgeArgument.capture());
        assertThat(edgeArgument.getValue(), is(sameBeanAs(expectedEdge)));
    }

    @Test
    public void testPathChosenPrintsShortestPath() throws Exception {
        Edge edge = anEdge().withSource("A").withDestination("B").withRouteId(1).withDistance(1).build();
        setUpFixture();
        String expectedPath = "Earth Moon ";
        when(edgeService.getEdgeList()).thenReturn(singletonList(edge));
        Vertex vertex2 = aVertex().withPlanetName("Moon").withNode("B").build();
        Vertex vertex1 = aVertex().withPlanetName("Earth").withNode("A").build();

//        when(verticesService.getVertexList()).thenReturn(Arrays.asList(vertex1, vertex2));
        when(verticesService.getVertexByNode("A")).thenReturn(vertex1);
        when(verticesService.getVertexByNode("B")).thenReturn(vertex2);
        mockMvc.perform(get("/selectPath/B")).andExpect(content().string(expectedPath));
    }

    @Test
    public void testTrafficChosenRoutePrintsShortestPath() throws Exception {
        Traffic traffic = aTraffic().withSource("A").withDestination("B").withRouteId(1).withDistance(1).build();
        setUpFixture();
        String expectedPath = "Earth Moon ";
        Vertex vertex2 = aVertex().withPlanetName("Moon").withNode("B").build();
        Vertex vertex1 = aVertex().withPlanetName("Earth").withNode("A").build();

        when(trafficService.getTrafficList()).thenReturn(singletonList(traffic));
        when(verticesService.getVertexByNode("A")).thenReturn(vertex1);
        when(verticesService.getVertexByNode("B")).thenReturn(vertex2);
        mockMvc.perform(get("/selectDelayedPath/B")).andExpect(content().string(expectedPath));
    }

    @Test
    public void testVertexGetsAddedCorrectly() throws Exception {
        when(verticesService.getVertexByNode("A")).thenReturn(null);
        setUpFixture();
        mockMvc.perform(get("/addVertex/Earth"));
        ArgumentCaptor<Vertex> vertexArgument = forClass(Vertex.class);
        verify(verticesService).persistVertex(vertexArgument.capture());
        String nodeId = vertexArgument.getValue().getNode();
        Vertex expectedVertex = aVertex().withNode(nodeId).withPlanetName("Earth").build();
        assertThat(vertexArgument.getValue(), is(sameBeanAs(expectedVertex)));
    }

    @Test
    public void testEdgeGetsAddedCorrectly() throws Exception {
//        when(verticesService.getVertexByNode("A")).thenReturn(null);
        setUpFixture();
        mockMvc.perform(get("/addEdge/LI,KI,4"));
        ArgumentCaptor<Edge> edgeArgument = forClass(Edge.class);
        verify(edgeService).persistEdge(edgeArgument.capture());
        int edgeId = edgeArgument.getValue().getRouteId();
        Edge expectedEdge = anEdge().withRouteId(edgeId).withSource("LI").withDestination("KI").withDistance(4).build();
        assertThat(edgeArgument.getValue(), is(sameBeanAs(expectedEdge)));
    }

    public void setUpFixture() {
        mockMvc = standaloneSetup(
                new RootController(edgeService,
                        verticesService,
                        trafficService)
        )
                .setViewResolvers(getInternalResourceViewResolver())
                .build();
    }

    private InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
}