package adpharma.client;

import java.util.UUID;


public class UUIDTest {

	public static void main(String[] args) {
		UUID randomUUID = UUID.randomUUID();
		randomUUID.timestamp();
		randomUUID.node();
		try {
			System.out.println(Long.toString(randomUUID.timestamp(), 36)+"-"+Long.toString(randomUUID.timestamp(), 36));
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			// noop
		}

	}

}
