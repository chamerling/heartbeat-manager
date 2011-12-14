import models.Host;

import org.junit.Test;

import play.test.UnitTest;

/**
 * 
 */

/**
 * @author chamerling
 * 
 */
public class HostTest extends UnitTest {

	@Test
	public void integrationTest() {
		int size = Host.findAll().size();
		Host host = new Host();
		host.hostname = "http://localhost";
		host.lastPing = 0L;
		host.lastSuccess = 0L;
		host.save();
		assertNotNull(host.id);
		assertTrue(Host.findAll().size() == size + 1);
		host.delete();
		assertTrue(Host.findAll().size() == size);
	}

}
