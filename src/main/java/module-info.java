open module dev.school.app.biblioapp {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.xml;
	requires java.logging;
	requires java.desktop;
	requires org.apache.pdfbox;
	requires de.jensd.fx.glyphs.fontawesome;
	requires org.junit.jupiter.api;
	requires org.testfx.junit5;
	requires org.testfx;
	requires transitive javafx.base;

	exports dev.school.app.biblioapp.controllers;
	exports dev.school.app.biblioapp.views;
	exports dev.school.app.biblioapp.models;
	exports dev.school.app.biblioapp;
}
