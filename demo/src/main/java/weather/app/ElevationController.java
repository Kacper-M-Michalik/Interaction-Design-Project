package weather.app;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.Node;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

//Kacper Michalik
public class ElevationController
{
    // GUI controls
    Button btnX, btnY, btnZ;
    ToggleButton btnHideShape, btnHideAxis;
    RadioButton optCamera, optShape;
    ToggleGroup tgpSwitch;
    CheckBox cbxWireframe;
    // On stage
    PerspectiveCamera pCamera;
    Group cast, xyzAxis;
    // Our pyramid shape
    MeshView MapMesh;
    // Actions
    //RotateAction rotShapeX, rotShapeY, rotShapeZ;
    RotateAction rotCamX, rotCamY, rotCamZ;

    int SceneW = App.ScreenWidth;
    int SceneH = App.ScreenHeight - 210; //-130
    
    float VertexSpacing = 20f;

    boolean IsDragging;
    double PreviousX = 0;
    double PreviousY = 0;
    
    public void start(Pane parentNode) {

        // Use a floawpane for the root node.
        FlowPane rootNode = new FlowPane(10, 10);
        // Center nodes in the scene
        rootNode.setAlignment(Pos.CENTER);
        parentNode.getChildren().add(rootNode);

        pCamera = new PerspectiveCamera(true);
        // Set the camera's rotation axis to Y axis
        // pCamera.setRotationAxis(Rotate.Y_AXIS);
        pCamera.getTransforms().addAll(new Translate(0, -150, -500));
        pCamera.setFieldOfView(45);
        pCamera.setFarClip(10000);
        pCamera.setNearClip(0);

        // Create sub scene to manage the group. Notice that a 
        // depth buffer is enabled
        Group groupAll = new Group();
        // Build the cast (shapes) and XYZ axis
        cast = GenerateTerrain();
        xyzAxis = (BuildAxis(1, 200));
        groupAll.getChildren().addAll(cast, xyzAxis);
        // Create the scene and set its camera
        SubScene shapesSub = new SubScene(groupAll, SceneW, SceneH, true,
                SceneAntialiasing.DISABLED);
        shapesSub.setFill(Color.AZURE);
        shapesSub.setCamera(pCamera);
        // Set up rotation axis
        //rotShapeX = new RotateAction(Rotate.X_AXIS, cast);
        //rotShapeY = new RotateAction(Rotate.Y_AXIS, cast);
        //rotShapeZ = new RotateAction(Rotate.Z_AXIS, cast);
        rotCamX = new RotateAction(Rotate.X_AXIS, pCamera);
        rotCamY = new RotateAction(Rotate.Y_AXIS, pCamera);
        rotCamZ = new RotateAction(Rotate.Z_AXIS, pCamera);

        // Create the GUI
        Pane gui = buildGUI(cast);

        rootNode.getChildren().addAll(shapesSub, gui);


        Stage MainStage = App.GetMainStage();
        MainStage.addEventHandler(ScrollEvent.SCROLL, event -> {
            pCamera.getTransforms().addAll(new Translate(0f, 0f, (float)event.getDeltaY()));
        });
        
        MainStage.addEventHandler(MouseDragEvent.MOUSE_PRESSED, event -> {
            IsDragging = true;
            PreviousX = event.getScreenX();
            PreviousY = event.getSceneY();
        });

        MainStage.addEventHandler(MouseDragEvent.MOUSE_RELEASED, event -> {
            IsDragging = false;
        });

        MainStage.addEventHandler(MouseDragEvent.MOUSE_MOVED, event -> {
            //pCamera.getTransforms().addAll(new Translate(100f,0f,0f));
            if (IsDragging)
            {

            }
        });



    }

    public Group BuildAxis(float rad, float size) {
        Cylinder xAxis = new Cylinder(rad, size);
        xAxis.setMaterial(new PhongMaterial(Color.RED));
        xAxis.setRotationAxis(Rotate.Z_AXIS);
        xAxis.setRotate(90);
        xAxis.setTranslateX(size / 2);

        Cylinder yAxis = new Cylinder(rad, size);
        yAxis.setMaterial(new PhongMaterial(Color.GREEN));
        yAxis.setTranslateY(size / 2);

        Cylinder zAxis = new Cylinder(rad, size);
        zAxis.setMaterial(new PhongMaterial(Color.BLUE));
        zAxis.setRotationAxis(Rotate.X_AXIS);
        zAxis.setRotate(90);
        zAxis.setTranslateZ(-rad + size / 2);

        xyzAxis = new Group();
        xyzAxis.getChildren().addAll(xAxis, yAxis, zAxis);

        return xyzAxis;
    }

    public Group GenerateTerrain() {
        TriangleMesh Mesh = new TriangleMesh();

        //45.8935f, 7.3924f
        LocationSearchResult TestResult = new LocationSearchResult(null, null, 47.203896f, 10.288914f);
        //WeatherAndLocationManager.CheckCachedElevationData(TestResult);
        WeatherAndLocationManager.LoadElevationData(TestResult);

        ElevationResult ElevationData =  WeatherAndLocationManager.CurrentElevationData;
        float[][] Elevations = WeatherAndLocationManager.CurrentElevationData.Elevations;

        float Delta = ElevationData.MaxElevation - ElevationData.MinElevation;    
        float VertexOffset = (Elevations.length - 1) * 0.5f * VertexSpacing;

        for (int y = 0; y < Elevations.length; y++)
        {
            for (int x = 0; x < Elevations[y].length; x++)
            {
                Mesh.getPoints().addAll(x * VertexSpacing - VertexOffset, -((Elevations[y][x] - ElevationData.MinElevation)/Delta)*250f, y * VertexSpacing - VertexOffset);
                Mesh.getTexCoords().addAll(x / (float)Elevations[y].length, y / (float)Elevations.length);
            }
        }

        for (int y = 0; y < Elevations.length - 1; y++)
        {
            for (int x = 0; x < Elevations[y].length - 1; x++)
            {
                int Index = y * Elevations[y].length + x;
                Mesh.getFaces().addAll(Index, Index, Index + Elevations[y].length, Index + Elevations[y].length, Index + 1, Index + 1);
                Mesh.getFaces().addAll(Index + 1, Index + 1, Index + Elevations[y].length, Index + Elevations[y].length, Index + Elevations[y].length + 1, Index + Elevations[y].length + 1);
            }
        }
        
        Mesh.getTexCoords().addAll(0f,0f);

        /*        
        // Vertex coordinates
        float h = 210;    // Height (Y)
        float w = 245;    // Width (X)
        float d = 125;    // Depth (Z)
        mesh.getPoints().addAll(
            0,      -h / 2,   0,        // 0
            w / 2,  h / 2,    d / 2,    // 1
            w / 2,  h / 2,    -d / 2,   // 2
            -w / 2, h / 2,    -d / 2,   // 3
            -w / 2, h / 2,    d / 2     // 4
        );
        // Add texture coordinates
        mesh.getTexCoords().addAll(
                0.504f, 0.524f,     // 0
                0.701f, 0,          // 1
                0.126f, 0,          // 2
                0,      0.364f,     // 3
                0,      0.608f,     // 4
                0.165f, 1,          // 5
                0.606f, 1,          // 6
                0.575f, 0.420f,     // 7
                0.575f, 0.643f,     // 8
                0.740f, 0.643f,     // 9
                0.740f, 0.420f      // 10      
        );
        mesh.getFaces().addAll(
                0, 0, 3, 5, 2, 6, // Front face
                0, 0, 2, 2, 1, 3, // Right face
                0, 0, 1, 1, 4, 2, // Back face
                0, 0, 4, 4, 3, 5, // Left right face
                2, 9, 3, 8, 4, 7, // Bottom face
                2, 9, 4, 7, 1, 10 // Bottom face
        );
        */

        PhongMaterial Material = new PhongMaterial(Color.WHITE);
        Material.setSpecularColor(Color.WHITE);
        Material.setDiffuseMap(GenerateDataTexture());

        MapMesh = new MeshView(Mesh);
        MapMesh.setDrawMode(DrawMode.FILL);        
        MapMesh.setMaterial(Material);
        MapMesh.setCullFace(CullFace.FRONT);

        AmbientLight al = new AmbientLight();
        al.setColor(Color.WHITE);
        
        PointLight pl = new PointLight(Color.BLUE);
        pl.setTranslateX(50);
        pl.setTranslateY(-225);  
       
        Group group = new Group();
        group.getChildren().addAll(MapMesh, al, pl);

        return group;
    }

    public Image GenerateDataTexture() {

        //Random Rnd = new Random();

        final int Size = WeatherAndLocationManager.CurrentElevationData.Elevations.length;

        WritableImage Img = new WritableImage(Size, Size);
        PixelWriter Writer = Img.getPixelWriter();

        float Delta = (WeatherAndLocationManager.CurrentElevationData.MaxElevation - WeatherAndLocationManager.CurrentElevationData.MinElevation);

        for (int x = 0; x < Size; x++) 
        {
            for (int y = 0; y < Size; y++) 
            {
                //Color color = Color.rgb(Rnd.nextInt( 256), Rnd.nextInt( 256), Rnd.nextInt( 256));
                Color color = Color.rgb(0, (int)(255f*(WeatherAndLocationManager.CurrentElevationData.Elevations[y][x]-WeatherAndLocationManager.CurrentElevationData.MinElevation)/Delta), 0);
                
                Writer.setColor(x, y, color);
            }
        }

        return Img;
    }

    public Pane buildGUI(Group shapeGroup) {
        FlowPane guiPane = new FlowPane(10, 10);
        guiPane.setAlignment(Pos.CENTER);
        btnX = new Button("<X>");
        btnY = new Button("<Y>");
        btnZ = new Button("<Z>");
        btnHideShape = new ToggleButton("Hide Shape");
        btnHideAxis = new ToggleButton("Hide Axis");
        tgpSwitch = new ToggleGroup();
        optCamera = new RadioButton("Camera");
        optCamera.setToggleGroup(tgpSwitch);
        optShape = new RadioButton("Shape");
        optShape.setToggleGroup(tgpSwitch);
        optShape.setSelected(true);
        cbxWireframe = new CheckBox("Wireframe");
        cbxWireframe.setSelected(false);

        guiPane.getChildren().addAll(btnX, btnY, btnZ,
                btnHideShape, btnHideAxis,
                optCamera, optShape, cbxWireframe);
        
        // Add event handlers
        btnX.setOnAction(rotCamX);
        btnY.setOnAction(rotCamY);
        btnZ.setOnAction(rotCamZ);

        //optShape.setOnAction((ActionEvent event) -> {
        //    btnX.setOnAction(rotShapeX);
        //    btnY.setOnAction(rotShapeY);
        //    btnZ.setOnAction(rotShapeZ);
        //});
        optCamera.setOnAction((ActionEvent event) -> {
            btnX.setOnAction(rotCamX);
            btnY.setOnAction(rotCamY);
            btnZ.setOnAction(rotCamZ);
        });
        btnHideShape.setOnAction((ActionEvent event) -> {
            cast.setVisible(!btnHideShape.isSelected());
        });

        btnHideAxis.setOnAction((ActionEvent event) -> {
            xyzAxis.setVisible(!btnHideAxis.isSelected());
        });

        cbxWireframe.setOnAction((ActionEvent event) -> {
            if (cbxWireframe.isSelected()) {
                MapMesh.setDrawMode(DrawMode.LINE);
            } else {
                MapMesh.setDrawMode(DrawMode.FILL);
            }
        });

        return guiPane;
    }

    public class RotateAction implements EventHandler<ActionEvent> {

        RotateTransition trans;

        RotateAction(Point3D a, Node group) {
            trans = new RotateTransition(new Duration(5000), group);
            trans.setAxis(a);
            trans.setCycleCount(2);
            trans.setAutoReverse(true);
            trans.setByAngle(360);
        }

        @Override
        public void handle(ActionEvent event) {
            trans.play();
        }
    }
   
}
