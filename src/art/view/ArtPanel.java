package art.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import art.controller.ArtController;
import art.view.ShapeCanvas;

public class ArtPanel extends JPanel
{

	private final int MINIMUM_EDGE = 5;
	private final int MAXIMUM_EDGE = 20;
	private final int MINIMUM_SCALE = 20;
	private final int MAXIMUM_SCALE = 100;
	
	private ArtController app;
	
	private SpringLayout appLayout;
	private ShapeCanvas Shapecanvas;
	private JPanel buttonPanel;
	private JPanel sliderPanel;
	private JSlider scaleSlider;
	private JSlider edgeSlider;
	private JButton triangleButton;
	private JButton rectangleButton;
	private JButton ellipseButton;
	private JButton polygonButton;
	private JButton clearButton;
	private JButton saveButton;
	private JButton colorButton;
	
	private int currentEdgeCount; 
	private int currentScale;
	
	public ArtPanel(ArtController app)
	{
		super();
		this.app = app;
		appLayout = new SpringLayout();
		
		currentScale = MINIMUM_SCALE;
		currentEdgeCount = MINIMUM_EDGE;
		scaleSlider = new JSlider(MINIMUM_SCALE, MAXIMUM_SCALE);
		edgeSlider = new JSlider(MINIMUM_EDGE, MAXIMUM_EDGE);
		
		Shapecanvas = new Shapecanvas(app);
		appLayout.putConstraint(SpringLayout.NORTH, Shapecanvas, 10, SpringLayout.NORTH, this);
		sliderPanel = new JPanel();
		appLayout.putConstraint(SpringLayout.EAST, sliderPanel, -220, SpringLayout.EAST, this);
		buttonPanel = new JPanel(new GridLayout(0,1));
		appLayout.putConstraint(SpringLayout.WEST, Shapecanvas, 6, SpringLayout.EAST, buttonPanel);
		appLayout.putConstraint(SpringLayout.SOUTH, sliderPanel, 0, SpringLayout.SOUTH, buttonPanel);
		
		triangleButton = new JButton("Add Triangle");
		rectangleButton =new JButton("Add Rectangle");
		ellipseButton = new JButton("Add Ellipse");
		polygonButton = new JButton("Add Polygon");
		clearButton = new JButton("Clear Image");
		saveButton = new JButton("Save Image");
		colorButton = new JButton("Change Color");
		
		setupSliders();
		setupPanel();
		setupLayout();
		setupListeners();
		
	}
	
	private void setupSliders()
	{
		Hashtable<Integer, JLabel> scaleLabels = new Hashtable<Integer, JLabel>();
		Hashtable<Integer,JLabel> edgeLabels = new Hashtable<Integer, JLabel>();
		
		scaleLabels.put(MINIMUM_SCALE, new JLabel("<HTML>Small<BR>Shape</HTML>"));
		scaleLabels.put((MAXIMUM_SCALE + MINIMUM_SCALE) / 2, new JLabel("<HTML>MediumMBR>Shae</HTML>"));
		scaleLabels.put(MAXIMUM_SCALE, new JLabel("<HTML> Large<BR>Shape</HTML>"));
		
		edgeLabels.put(MINIMUM_EDGE, new JLabel("Edges: " + MINIMUM_EDGE));
		edgeLabels.put(MAXIMUM_EDGE,  new JLabel("Edges: " + MAXIMUM_EDGE));
		
		
		scaleSlider.setLabelTable(scaleLabels);
		scaleSlider.setOrientation(JSlider.VERTICAL);
		scaleSlider.setSnapToTicks(true);
		scaleSlider.setMajorTickSpacing(10);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		scaleSlider.setValue(MINIMUM_SCALE);
		
		edgeSlider.setLabelTable(edgeLabels);
		edgeSlider.setOrientation(JSlider.VERTICAL);
		edgeSlider.setSnapToTicks(true);
		edgeSlider.setMajorTickSpacing(3);
		edgeSlider.setMinorTickSpacing(1);
		edgeSlider.setPaintTicks(true);
		edgeSlider.setPaintLabels(true);
	}
	
	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(1024, 768));; //Used for display in WindowBuilder
		this.add(Shapecanvas);
		
		buttonPanel.setPreferredSize(new Dimension(200,450));
		buttonPanel.add(triangleButton);
		buttonPanel.add(rectangleButton);
		buttonPanel.add(ellipseButton);
		buttonPanel.add(polygonButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(colorButton);
		
		sliderPanel.setPreferredSize(new Dimension(250,450));
		sliderPanel.add(scaleSlider);
		sliderPanel.add(edgeSlider);
		
		this.add(buttonPanel);
		this.add(sliderPanel);
	}
	
	private void setupLayout()
	{
		
	}
	
	private boolean coinFLip()
	{
		return (int) (Math.random() * 2) == 0;
	}
	
	private Polygon createPolygon(int sides)
	{
		Polygon currentShape = new Polygon();
		
		int originX = (int) (Math.random() * 600);
		int originY = (int) (Math.random() * 600);
		
		for (int index = 0; index < sides; index++)
		{
			int minus = coinFlip() ? -1 : 1;
			int shiftX = (int) (Math.random() * currentScale) * minus;
			minus = coinFlip() ? -1 : 1;
			int shiftY = (int) (Math.random() * currentScale) * minus ;
			currentShape.addPoint(originX + shiftX, originY + shiftY);
		}
		return currentShape;
	}
	
	
		private Rectangle createRectangle()
		{
			Rectangle currentRectangle;
			int cornerX = (int) (Math.random() * 600);
			int cornerY = (int) (Math.random() * 600);
			int width = (int)(Math.random() * currentScale) + 1;
			if(coinFlip())
			{
				currentRectangle = new Rectangle(cornerX, cornerY, width, width);
			}
			else
			{
				int height = (int)(Math.random() * currentScale) + 1;
				currentRectangle = new Rectangle(cornerX, cornerY,  width, height);
			}
			return currentRectangle;
				
		}
		private Ellipse createEllipse()
		{
			Ellipse2D ellipse = new Ellipse2D.Double();
			
			int cornerX = (int) (Math.random() * 600);
			int cornerY = (int) (Math.random() * 600);
			int width = (int)(Math.random() * currentScale) + 1;
			if(coinFlip())
			{
				ellipse.setFrame(cornerX, cornerY, width, width);
			}
			else
			{
				double height = (int)(Math.random() * currentScale) + 1;
				ellipse.setFrame(cornerX, cornerY, width, height);
			}
			return ellipse;
				
		}
		
	private void setupListeners()
	{	
		rectangleButton.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent click)
		{
			Rectangle rectangle = createRectangle();
			Shapecanvas.addShape(rectangle);
		}
		
		});
		triangleButton.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent click)
		{
			Polygon triangle = createPolygon(3);
			Shapecanvas.addShape(triangle);
		}
		
		});
		ellipseButton.addActionListener(new ActionListener()
			{
			public void actionPerformed(ActionEvent click)
			{
				Ellipse2D ellipse = createEllipse();
				Shapecanvas.addShape(ellipse);
			}
			
			});
		
		clearButton.addActionListener(click -> Shapecanvas.clear());
		
		saveButton.addActionListener(click -> Shapecanvas.save());
		
		colorButton.addActionListener(click -> Shapecanvas.changeBackground();
		
		scaleSlider.addChangeListener(new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					if (!edgeSlider.getValueIsAdjusting())
					{
						currentEdgeCount = edgeSlider.getValue();
					}
				}
			
			});
		
	}
}
