package ru.ibelan.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class StoreMessageScreen extends Screen {
	private static final String INPUT_TEXT_SCREEN_TITLE = "Store Message";
	private static final int SPACING = 10;

	private final Consumer<String> sendHandler;

	private TextFieldWidget textField;
	private ButtonWidget sendButton;
	private ButtonWidget closeButton;

	public StoreMessageScreen(Consumer<String> sendHandler) {
		super(Text.of(INPUT_TEXT_SCREEN_TITLE));
		this.sendHandler = sendHandler;
	}

	@Override
	protected void init() {
		initInputBar();
		initCloseButton();

		setInitialFocus(textField);
	}

	private void initInputBar() {
		int barHeight = 20;
		int barY = height/2 - barHeight/2;

		int sendButtonWidth = 100;
		int sendButtonX = width - sendButtonWidth - SPACING;

		int inputFieldWidth = width - sendButtonWidth - SPACING * 3;
		int inputFieldX = SPACING;

		textField = new TextFieldWidget(textRenderer, inputFieldX, barY, inputFieldWidth, barHeight, Text.empty());
		sendButton = ButtonWidget
				.builder(Text.of("Send"), button -> onSend())
				.dimensions(sendButtonX, barY, sendButtonWidth, barHeight)
				.build();

		addDrawableChild(textField);
		addDrawableChild(sendButton);
	}

	private void initCloseButton() {
		Text closeButtonTitle = Text.of("Close");
		int buttonWidth = 100;
		int buttonHeight = 20;
		int buttonX = width - buttonWidth - SPACING;
		int buttonY = height - buttonHeight - SPACING;
		closeButton = ButtonWidget
				.builder(closeButtonTitle, button -> close())
				.dimensions(buttonX, buttonY, buttonWidth, buttonHeight)
				.build();

		addDrawableChild(closeButton);
	}

	private void showNotifier() {
		assert client != null;
		client.getToastManager().add(SystemToast.create(
				client,
				SystemToast.Type.NARRATOR_TOGGLE,
				Text.of("SUCCESS"),
				Text.of("The message has been sent.")));
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ENTER) {
			onSend();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	private void onSend() {
//		sendButton.active = false;
//		textField.active = false;
//		sendButton.setMessage(Text.of("Sending..."));

		String message = textField.getText();
		if (message != null && !message.isEmpty()) {
			sendHandler.accept(message);
			showNotifier();
		}
		textField.setText("");
//		close();

//		sendButton.setMessage(Text.of("Send"));
//		sendButton.active = true;
//		textField.active = false;
//		textField.setFocused(true);
	}
}
