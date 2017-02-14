package castle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.sun.org.omg.CORBA.ExceptionDescription;

public class Game {
	private Room currentRoom;
	private HashMap<String, Handler> handlers = new HashMap<String, Handler>();
	private HashMap<String, String> directionReverse = new HashMap<String, String>();
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	private String mapStr = "";

	public Game() {
		handlers.put("bye", new HandlerBye(this));
		handlers.put("help", new HandlerHelp(this));
		handlers.put("go", new HandlerGo(this));
		createRooms();
	}

	private Game setMapStr(String mapStr) {
		this.mapStr = mapStr;
		return this;
	}

	public String getMapStr() {
		return mapStr;
	}

	// 增加一对互逆方向
	private Game addReverseDirection(String d1, String d2) {
		// 如果d1或d2已定义了方向绑定，则先解除与其互逆方向字符串的绑定
		if (directionReverse.containsKey(d1))
			directionReverse.remove(directionReverse.get(d1));
		if (directionReverse.containsKey(d2))
			directionReverse.remove(directionReverse.get(d2));
		directionReverse.put(d1, d2);
		directionReverse.put(d2, d1);
		return this;
	}

	// room1 的 direction 方向上是 room2， 如果这是一个已定义的互逆方向，则room2的相反方式上是room1
	private Game setRoomExit(String room1Key, String direction, String room2Key) {
		Room room1 = rooms.get(room1Key);
		Room room2 = rooms.get(room2Key);
		room1.setExit(direction, room2);
		if (directionReverse.containsKey(direction)) {
			String reverse = directionReverse.get(direction);
			room2.setExit(reverse, room1);
		}
		return this;
	}

	private Game setCurrentRoom(String roomKey) {
		currentRoom = rooms.get(roomKey);
		return this;
	}

	// 增加Game内Room
	private Game addRoom(String roomKey, String roomName) {
		rooms.put(roomKey, new Room(roomName));
		return this;
	}

	private void createRooms() {
		// 配置互逆方向
		this.addReverseDirection("left", "right").addReverseDirection("top", "bottom").addReverseDirection("up", "down")
				// 制造房间
				.addRoom("A", "roomA").addRoom("B", "roomB").addRoom("C", "roomC").addRoom("D", "roomD")
				.addRoom("E", "roomE").addRoom("F", "roomF").addRoom("G", "roomG").addRoom("H", "roomH")
				.addRoom("O", "大厅").addRoom("loft", "阁楼")

				// 配置相对关系
				.setRoomExit("A", "top", "B").setRoomExit("B", "right", "C").setRoomExit("C", "right", "D")
				.setRoomExit("D", "bottom", "E").setRoomExit("E", "bottom", "F").setRoomExit("F", "left", "G")
				.setRoomExit("G", "left", "H").setRoomExit("H", "up", "loft").setRoomExit("H", "top", "A")
				.setRoomExit("O", "left", "A").setRoomExit("O", "right", "E")
				// 从房间A开始
				.setCurrentRoom("O");

		// 设置地图字符串
		StringBuffer sb = new StringBuffer();
		sb.append("   +-----+-----+-----+               ^ top          \n")
			.append("   |     |     |     |               |              \n")
			.append("   |  B     C     D  |               |              \n")
			.append("   |     |     |     |               |              \n")
			.append("   +-- ----------- --+               |              \n")
			.append("   |     |     |     |  left         |       right  \n")
			.append("   |  A     O     E  |     <---------x---------->   \n")
			.append("   |     |     |     |               |              \n")
			.append("   +-- ----------- --+               |              \n")
			.append("   +-+   |     |     |               |              \n")
			.append("   |.|H     G     F  |               |              \n")
			.append("   +-+   |     |     |               |              \n")
			.append("   +-----+-----+-----+               v bottom       \n")
			.append("                                                    \n")
			.append("   +-----------+                                    \n")
			.append("   +-+         |              +-+       +-+         \n")
			.append("   |x|  loft   |              |x| down  |.| up      \n")
			.append("   +-+         |              +-+       +-+         \n")
			.append("   +-----------+                                    \n");
		this.setMapStr(sb.toString());
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("欢迎来到城堡！");
		System.out.println("这是一个超级无聊的游戏。");
		System.out.println("如果需要帮助，请输入 'help' 。");
		System.out.println();
		showPrompt();
	}

	public void goRoom(String direction) {
		Room nextRoom = currentRoom.getExit(direction);

		if (nextRoom == null) {
			System.out.println("那里没有门！");
		} else {
			currentRoom = nextRoom;
			showPrompt();
		}
	}

	public void showPrompt() {
		System.out.println("你在" + currentRoom);
		System.out.print("出口有: ");
		System.out.println(currentRoom.getExitDesc());
		System.out.println();
	}

	public void play() {
		Scanner in = new Scanner(System.in);
		while (true) {
			String line = in.nextLine();
			String[] words = line.split(" ");
			Handler handler = handlers.get(words[0]);
			String cmd = "";
			if (words.length > 1) {
				cmd = words[1];
			}
			if (handler != null) {
				handler.doCmd(cmd);
				if (handler.isBye())
					break;
			} else {
				System.out.println("命令错误 ╭(╯^╰)╮");
			}
		}
		in.close();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.printWelcome();

		game.play();

		System.out.println("感谢您的光临。再见！");
	}

}
