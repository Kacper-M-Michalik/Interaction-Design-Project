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
import javafx.scene.chart.Axis;
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
    PerspectiveCamera pCamera;
    Group cast, xyzAxis;
    MeshView MapMesh;

    int SceneW = App.ScreenWidth;
    int SceneH = App.ScreenHeight - 105; //-130
    
    float VertexSpacing = 20f;

    boolean SkippedFirstClick = false;
    boolean IsDragging = false;
    double PreviousX = 0;
    double PreviousY = 0;
    double Theta = 270;
    double Gamma = 10;
    double R = 700;
    Rotate PrevXRotate;
    Rotate PrevYRotate;
    Translate CameraOffset;
    Point3D RotateUp = Rotate.Y_AXIS;
    Point3D RotateRight = Rotate.X_AXIS;
    
    public void start(Pane parentNode) {

        // Use a floawpane for the root node.
        FlowPane rootNode = new FlowPane(10, 10);
        
        rootNode.setAlignment(Pos.CENTER);
        parentNode.getChildren().add(rootNode);

        pCamera = new PerspectiveCamera(true);
        // Set the camera's rotation axis to Y axis
        // pCamera.setRotationAxis(Rotate.Y_AXIS);
        //pCamera.getTransforms().addAll(new Translate(0, -150, -500));
        pCamera.setFieldOfView(45);
        pCamera.setFarClip(10000);
        pCamera.setNearClip(0);
        PrevXRotate = new Rotate(-30, 0,0,0, RotateRight);
        //PrevXRotate = new Rotate(Gamma - 90, 0,0,0, RotateRight);
        PrevYRotate = new Rotate(Theta - 180, 0, 0, 0, RotateUp);
        CameraOffset = new Translate(0, -500, -1000);
        pCamera.getTransforms().addAll(CameraOffset, PrevXRotate);//, new Translate(-419.9878780390644, -181.17333157176452, -529.8928252273581));

        Group groupAll = new Group();
        cast = GenerateTerrain();        
        xyzAxis = (BuildAxis(1, 200));
        groupAll.getChildren().addAll(cast, xyzAxis);
        SubScene shapesSub = new SubScene(groupAll, SceneW, SceneH, true,
                SceneAntialiasing.DISABLED);
        shapesSub.setFill(Color.AZURE);
        shapesSub.setCamera(pCamera);
        
        Pane gui = buildGUI(cast);
        rootNode.getChildren().addAll(shapesSub);
        
        //RotateCamera();
        
        Stage MainStage = App.GetMainStage();
        MainStage.addEventHandler(ScrollEvent.SCROLL, event -> {
            R -= (double)event.getDeltaY();
            if (R < 150) R = 150;

            //RotateCamera();
        });
        
        MainStage.addEventHandler(MouseDragEvent.MOUSE_PRESSED, event -> {
            if (!SkippedFirstClick){
                SkippedFirstClick = true;
                return;
            }

            IsDragging = true;
            PreviousX = event.getScreenX();
            PreviousY = event.getSceneY();
        });

        MainStage.addEventHandler(MouseDragEvent.MOUSE_RELEASED, event -> {
            IsDragging = false;
        });

        MainStage.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, event -> {
            if (IsDragging)
            {
                double CurrentX = event.getScreenX();
                double CurrentY = event.getScreenY();

                double DeltaX = CurrentX - PreviousX;
                double DeltaY = CurrentY - PreviousY;

                Theta += DeltaX;
                Gamma += DeltaY;
                if (Gamma < 10) Gamma = 10;
                if (Gamma > 80) Gamma = 80;

                //RotateCamera();

                PreviousX = CurrentX;
                PreviousY = CurrentY;
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
        WeatherAndLocationManager.LoadElevationData(TestResult);

        ElevationResult ElevationData =  WeatherAndLocationManager.CurrentElevationData;
        float[][] Elevations = WeatherAndLocationManager.CurrentElevationData.Elevations;

        float Delta = ElevationData.MaxElevation - ElevationData.MinElevation;    
        float VertexOffset = (Elevations.length - 1) * 0.5f * VertexSpacing;

        for (int y = 0; y < Elevations.length; y++)
        {
            for (int x = 0; x < Elevations[y].length; x++)
            {
                Mesh.getPoints().addAll(x * VertexSpacing - VertexOffset, -((Elevations[y][x] - ElevationData.MinElevation)/Delta)*100f, y * VertexSpacing - VertexOffset);
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

        PhongMaterial Material = new PhongMaterial(Color.WHITE);
        Material.setSpecularColor(Color.WHITE);
        Material.setDiffuseMap(GenerateDataTexture());

        MapMesh = new MeshView(Mesh);
        MapMesh.setDrawMode(DrawMode.FILL);        
        MapMesh.setMaterial(Material);
        MapMesh.setCullFace(CullFace.NONE);
        MapMesh.setTranslateX(0);
        MapMesh.setTranslateY(0);
        MapMesh.setTranslateZ(0);

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
        /*
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
        */
        return guiPane;
    }

    public void RotateCamera()
    { 
        //Point3D cameraPosition = new Point3D(pCamera.getTranslateX(), pCamera.getTranslateY(), pCamera.getTranslateZ());

        double TR = Math.toRadians(Theta);
        double GR = Math.toRadians(Gamma);

        pCamera.getTransforms().addAll(new Translate(-CameraOffset.getX(), -CameraOffset.getY(), -CameraOffset.getZ()));
            
       //LookAt();
        
        CameraOffset = new Translate(R * Math.cos(TR) * Math.sin(GR), 
        -R * Math.cos(GR), 
        //-200,
        R * Math.sin(TR) * Math.sin(GR));

        //CameraOffset = new Translate(0, 0 ,R);
        
        pCamera.getTransforms().addAll(CameraOffset);

        //pCamera.setTranslateX(R * Math.cos(TR) * Math.sin(GR));
        //pCamera.setTranslateY(-R * Math.cos(GR));
        //pCamera.setTranslateY(-400);
        //pCamera.setTranslateZ(R * Math.sin(TR) * Math.sin(GR));
        
        System.out.println("POS");
        System.out.println(CameraOffset.getX());
        System.out.println(CameraOffset.getY());
        System.out.println(CameraOffset.getZ());
        System.out.println(R);
        System.out.println(Theta);
        System.out.println(Gamma);

        LookAt();
    }

    public void LookAt() 
    {                
                //double xRotation = Math.toDegrees(Math.asin(-camDirection.getY()));  
        //double yRotation =  Math.toDegrees(Math.atan2( camDirection.getX(), camDirection.getZ()));  
        //double xRotation = Math.toDegrees(Math.asin(-camDirection.getY()));  
        //double yRotation =  Math.toDegrees(Math.atan2( camDirection.getX(), camDirection.getZ()));
          
        //Rotate rx = new Rotate(, cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ(), new Point3D(1, 0, 0));  
        
        Rotate UndoX = new Rotate(-PrevXRotate.getAngle(), 0, 0, 0, RotateRight); 
        Rotate UndoY = new Rotate(-PrevYRotate.getAngle(), 0, 0, 0, RotateUp); 
        //Rotate Rx = new Rotate(Gamma, 0, 0, 0, RotateRight);  
        Rotate Rx = new Rotate(-45, 0, 0, 0, RotateRight);  
        Rotate Ry = new Rotate(Theta - 180, 0, 0, 0, RotateUp);  
        
        pCamera.getTransforms().addAll( 
            //new Translate (  
           //     -cameraPosition.getX(),   
            //    -cameraPosition.getY(),   
            //    -cameraPosition.getZ()
           // ),
            //UndoY, 
            UndoX,
            Rx
            //Ry
            //new Translate (  
            //    cameraPosition.getX(),   
            //    cameraPosition.getY(),   
            //    cameraPosition.getZ()
            //)
        );       

        PrevXRotate = Rx;
        PrevYRotate = Ry;                     
    }  

   
}
