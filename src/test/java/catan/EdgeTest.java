package catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test cases for the Edge Class
 *
 * @author Serene Abou Sharaf
 * March 5, 2026
 */
public class EdgeTest {
	private Edge edge;
	private Node nodeA;
	private Node nodeB;
	private Board board;

	@BeforeEach
	public void setUp(){

        nodeA = new Node(1);
        nodeB = new Node(2);
        edge = new Edge(nodeA, nodeB);

	}

    @Test
    void testGetNodeA() {
        assertEquals(nodeA, edge.getNodeA());
    }

    @Test
    void testGetNodeB() {
        assertEquals(nodeB, edge.getNodeB());
    }

    @Test
    void testGetRoadInitiallyNull() {
        assertNull(edge.getRoad());
        assertFalse(edge.hasRoad());
    }

    @Test
    void testPlaceRoad() {
        Road road = new Road(1, edge);
        edge.placeRoad(road);

        assertEquals(road, edge.getRoad());
        assertTrue(edge.hasRoad());

        //Cannot place another road on the same edge using try-catch
        try {
            edge.placeRoad(new Road(2, edge));
            fail("Expected IllegalStateException when placing a second road on the same edge");

        } catch (IllegalStateException e) {
            assertEquals("Edge already has a Road", e.getMessage());
        }
    }

    @Test
    void testGetOppositeNode() {
        assertEquals(nodeB, edge.getOppositeNode(nodeA));
        assertEquals(nodeA, edge.getOppositeNode(nodeB));

        //Using try catch for invalid node
        try {
            edge.getOppositeNode(new Node(99));
            fail("Expected IllegalArgumentException when passing a node not on the edge");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("does not correspond to an edge"));
        }
    }

    @Test
    void testSharesNode() {
        Node otherNode = new Node(3);
        assertTrue(edge.sharesNode(nodeA));
        assertTrue(edge.sharesNode(nodeB));
        assertFalse(edge.sharesNode(otherNode));
    }

    @Test
    void testIsAdjacentTo() {
        Node nodeC = new Node(3);
        Edge adjacentEdge = new Edge(nodeB, nodeC);
        Edge nonAdjacentEdge = new Edge(new Node(4), new Node(5));

        assertTrue(edge.isAdjacentTo(adjacentEdge));
        assertFalse(edge.isAdjacentTo(nonAdjacentEdge));
    }

    @Test
    void testEdgeNodesNotNull() {
        assertNotNull(edge.getNodeA());
        assertNotNull(edge.getNodeB());
    }


}
