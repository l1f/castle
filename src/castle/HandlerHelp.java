package castle;

public class HandlerHelp extends Handler {
	public HandlerHelp(Game game) {
		super(game);
	}

	@Override
	public void doCmd(String arg) {
		System.out.print("迷路了吗？你可以做的命令有：go bye help\t");
		System.out.println("如： go left");
		if (game.getMapStr() != "") {
			System.out.println("彩蛋:输入\"help map\",查看上帝视角全景地图");

		}
		if (arg.equals("map")) {
			System.out.println("上帝视角全景地图如下:");
			System.out.println(game.getMapStr());
		}
	}

}
