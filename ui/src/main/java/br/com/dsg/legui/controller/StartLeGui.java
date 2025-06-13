package br.com.dsg.legui.controller;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Vector2i;
import org.joml.Vector4f;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.Animator;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.Renderer;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.theme.colored.FlatColoredTheme;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

import br.com.dsg.legui.controller.eventos.EventAdicionarItemMenu;
import br.com.dsg.legui.controller.seguranca.SeguracaController;
import br.com.dsg.legui.controller.seguranca.UsuarioPrincipal;
public class StartLeGui {

	private static volatile boolean running = false;
	private static long[] monitors = null;
	private static boolean toggleFullscreen = false;
	private static boolean fullscreen = false;

	private static Context context;

	private Frame frame;

	private static StartLeGui instance = null;

	private int WIDTH = 600;
	private int HEIGHT = 400;
	private LeGuiView leGuiView = null;
	private DefaultInitializer initializer = null;
	private long window = 0;

	private LeGuiController leGuiController = null;
	
	public static final Theme THEME_CONTEUDO = new FlatColoredTheme(
	        rgba(44, 62, 80, 1), // backgroundColor
	        rgba(127, 140, 141, 1), // borderColor
	        rgba(127, 140, 141, 1), // sliderColor
	        rgba(2, 119, 189, 1), // strokeColor
	        rgba(39, 174, 96, 1), // allowColor
	        rgba(192, 57, 43, 1), // denyColor
	        rgba(0, 0, 0, 1f),  // shadowColor
	        ColorConstants.white(),
	        FontRegistry.getDefaultFont(),
	        18f
	    );
	
	 public static final Theme THEME_MENU = new FlatColoredTheme(
		        rgba(33, 33, 33, 1), // backgroundColor
		        rgba(97, 97, 97, 1), // borderColor
		        rgba(97, 97, 97, 1), // sliderColor
		        rgba(2, 119, 189, 1), // strokeColor
		        rgba(27, 94, 32, 1), // allowColor
		        rgba(183, 28, 28, 1), // denyColor
		        rgba(250, 250, 250, 0.5f),  // shadowColor
		        ColorConstants.white(),
		        FontRegistry.getDefaultFont(),
		        18f
		    );
	
	private Theme themeConteudo = THEME_CONTEUDO;
	private Theme themeMenu = THEME_MENU;

	private StartLeGui(int width, int height, String appNome) {

		System.setProperty("app.nome.sistema", "AppLegui");
		System.setProperty("app.frame.largura", String.valueOf(width));
		System.setProperty("app.frame.altura", String.valueOf(height));
		System.setProperty("app.bar.altura", "10");
		System.setProperty("app.largura.menu.fechado", "50");
		System.setProperty("app.largura.menu.aberto", "200");
		System.setProperty("app.altura.item.menu", "40");
		System.setProperty("app.altura.inicial.item.menu", "50");
		System.setProperty("app.altura.progress.bar", "10");
		
		
		
		System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
		System.setProperty("java.awt.headless", Boolean.TRUE.toString());

		if (!glfwInit()) {
			throw new RuntimeException("Can't initialize GLFW");
		}
		
		WIDTH = width;
		HEIGHT = height;
		
		window = glfwCreateWindow(WIDTH, HEIGHT, appNome, NULL, NULL);
		glfwShowWindow(window);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(0);

		PointerBuffer pointerBuffer = glfwGetMonitors();
		int remaining = pointerBuffer.remaining();
		monitors = new long[remaining];
		for (int i = 0; i < remaining; i++) {
			monitors[i] = pointerBuffer.get(i);
		}

			
		// Firstly we need to create frame component for window.
		frame = new Frame(WIDTH, HEIGHT);// new Frame(WIDTH, HEIGHT);

		leGuiView = new LeGuiView(this.themeMenu, this.themeConteudo, WIDTH, HEIGHT);
		leGuiView.setFocusable(false);
		leGuiController = new LeGuiController(leGuiView);

		leGuiView.getListenerMap().addListener(WindowSizeEvent.class,
				(WindowSizeEventListener) event -> leGuiController.getPanel().setSize(event.getWidth(), event.getHeight()));
		
		frame.getContainer().add(leGuiController.getPanel());
	}

	public static StartLeGui get(int width, int height, String appNome) {
		return instance =  new StartLeGui( width,  height,  appNome) ;
	}
	
	private static Vector4f rgba(int r, int g, int b, float a) {
        return new Vector4f(r / 255f, g / 255f, b / 255f, a);
    }

	public void start() {
		Themes.setDefaultTheme(this.themeConteudo);
		this.leGuiView.update();

		// We need to create legui instance one for window
		// which hold all necessary library components
		// or if you want some customizations you can do it by yourself.
		initializer = new DefaultInitializer(window, frame);

		GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action,
				mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
		
		GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

		// if we want to create some callbacks for system events you should create and
		// put them to keeper
		//
		// Wrong:
		// glfwSetKeyCallback(window, glfwKeyCallbackI);
		// glfwSetWindowCloseCallback(window, glfwWindowCloseCallbackI);
		//
		// Right:
		initializer.getCallbackKeeper().getChainKeyCallback().add(glfwKeyCallbackI);
		initializer.getCallbackKeeper().getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

		// Initialization finished, so we can start render loop.
		running = true;

		// Everything can be done in one thread as well as in separated threads.
		// Here is one-thread example.

		// before render loop we need to initialize renderer
		Renderer renderer = initializer.getRenderer();
		Animator animator = AnimatorProvider.getAnimator();
		renderer.initialize();

		context = initializer.getContext();

		this.leGuiController.loadController();
		while (running) {

			// Also we can do it in one line
			context.updateGlfwWindow();
			Vector2i windowSize = context.getFramebufferSize();

			glClearColor(1, 1, 1, 1);
			// Set viewport size
			glViewport(0, 0, windowSize.x, windowSize.y);
			// Clear screen
			glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

			LayoutManager.getInstance().layout(frame);

			// render frame
			renderer.render(frame, context);

			// poll events to callbacks
			glfwPollEvents();
			glfwSwapBuffers(window);

			animator.runAnimations();

			// Now we need to handle events. Firstly we need to handle system events.
			// And we need to know to which frame they should be passed.
			initializer.getSystemEventProcessor().processEvents(frame, context);

			// When system events are translated to GUI events we need to handle them.
			// This event processor calls listeners added to ui components
			initializer.getGuiEventProcessor().processEvents();
			if (toggleFullscreen) {
				if (fullscreen) {
					glfwSetWindowMonitor(window, NULL, 100, 100, WIDTH, HEIGHT, GLFW_DONT_CARE);
				} else {
					GLFWVidMode glfwVidMode = glfwGetVideoMode(monitors[0]);
					glfwSetWindowMonitor(window, monitors[0], 0, 0, glfwVidMode.width(), glfwVidMode.height(),
							glfwVidMode.refreshRate());
				}
				fullscreen = !fullscreen;
				toggleFullscreen = false;
			}
			
			if(running)
				running = !leGuiController.isAppFinalizado();
			
		}
		// And when rendering is ended we need to destroy renderer
		renderer.destroy();
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public StartLeGui setThemeConteudo(Theme themeConteudo) {
		this.themeConteudo = themeConteudo;
		this.leGuiView.setThemeConteudo(themeConteudo);
		return this;
	}

	public StartLeGui setThemeMenu(Theme themeMenu) {
		this.themeMenu = themeMenu;
		this.leGuiView.setThemeMenu(themeMenu);
		return this;
	}

	public StartLeGui setAppProperty(String chave, String valor) {
		System.setProperty(chave, valor);
		return this;
	}
	
	public StartLeGui abrirFecharMenuPadrao() {
		this.leGuiController.addItemMenu(new EventAdicionarItemMenu(
				"",
				"imagens/icons8-cardapio-30.png",
				"imagens/icons8-cardapio-fechado-30.png", 
				true, 
				true,
				(c) -> c.menuAbrirFecharMenu() )
		);
		return this;
	}
	
	public StartLeGui addItemMenu(EventAdicionarItemMenu itemMenu) {
		this.leGuiController.addItemMenu( itemMenu );
		return this;
	}
	
	public StartLeGui menuFechado() {
		this.leGuiController.menuFechado();
		return this;
	}
	
	public <T extends AbstractController<?>> StartLeGui controllerPrincipall(GerarController<T> cController ) {
		this.leGuiController.controllerPrincipall(cController);
		return this;
	}

	public <T extends SeguracaController<? extends UsuarioPrincipal,? extends Panel>> StartLeGui autenticacao( GerarController<T> cController) {
		this.leGuiController.autenticacao(cController);
		return this;
	}

//	public static void main(String[] args) {
//		StartLeGui.get(800, 600, "App test")
//			//.addItemMenu("Home","/imagens/home_house_10811.png", (controllerPai)->new HomeController(controllerPai), Boolean.TRUE)
//			//.addItemMenu("Configuração","/imagens/setting-configure.png",  (controllerPai)->new ConfigController(controllerPai))
//			//.addItemMenu( "Home","imagens/home_house_10811.png",  c -> System.out.println("Item 1") )
//			.addItemMenu( "Configuração","imagens/setting-configure.png",  controler -> ((LeGuiController)controler).abrirFecharMenu() )
//			.addItemMenu( "Sair","imagens/icons8-exit-sign-64.png",  (controler) -> System.out.println("Sair") )
//			.fecharMenu()
//			.start();
//	}


}
