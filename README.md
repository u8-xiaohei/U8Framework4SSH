# U8Framework4SSH

U8Server中使用的SSH2(Struts2+Spring3+Hibernate4)框架，包括U8Server集成好的配置文件，以及抽象出来的一些通用组件，使得基于J2EE框架开发的同学可以快速展开工作。

U8Server是U8SDK统一渠道SDK接入框架的服务器端。U8Server采用成熟的SSH2(J2EE框架)研发。性能可靠，结构简单。

采用SSH2框架研发的项目，开发其实很简单。主要就是框架的整合和搭建，需要花费点时间。问题主要集中在配置方式和框架的使用。

之前很多同学在问能否将U8Server采用的配置和整个框架，这里，我们将U8Server采用的框架和配置开源出来。需要采用SSH2研发的项目，可以直接在该框架基础上进行，免去了从头配置的繁琐。

整个框架，基于MVC思想模式，同时所有配置通过注解进行。避免通过配置文件配置带来的繁琐和复杂。

依赖Jar包：

lib/jars目录下为SSH2框架所依赖的一些jar包。添加新的jar包注意，不要导致jar包冲突。

框架配置说明：

applicationContext.xml：Spring容器的配置，所有的组件管理和依赖注入(IOC)都由Spring管理，基本固定，不用修改
jdbc.properties：数据库相关配置，在这里修改数据库连接信息即可
log4j.properties：日志配置，基本固定，不用修改
struts.xml：struts2的配置，因为我们采用注解配置方式，所以该文件配置很少，而且固定
WEB-INF\web.xml：这个是web项目的固定配置，需要配置struts2,spring,hibernate等的过滤器和监听器等。基本固定，不用修改

源码目录（根为com.u8.server）：

cache/	:缓存相关类目录。U8Server中对游戏对象，渠道对象，渠道商对象进行了缓存
common/ :该目录下为U8Server对SSH2框架的一个简单封装
dao/	:MVC模式典型的DAO层(数据访问层)
data/	:数据对象
filters/:struts2过滤器，比如后台管理操作，需要登录用户和拥有相关权限的用户才能进行，通过过滤器来拦截
log/	:日志目录
service/:MVC模式典型的Service层(业务逻辑层)
utils/	:常用辅助工具类
web/	:MVC模式典型的Web层，这里写struts2的Action

框架使用：

比如现在需要开发一个用户管理系统，

1、我们会抽象出来一个User对象。那么在data目录下，新建一个UUser对象：

/**
 * 用户数据对象
 * 约定：所有字段的名称，都需要和数据库表格中的字段名称一致
 */
@Entity //说明该对象为一个数据类
@Table(name = "uuser") //该数据库和数据库中某张表格一一对应。name为数据库表的名称
public class UUser {

    @Id //说明该字段为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //主键生成方式，这里自动递增。具体其他方式，自行查阅资料了解
    private int id;
    

    private int appID;
    private int channelID;
    private String name;
    private String channelUserID;
    private String channelUserName;
    private String channelUserNick;
    private Date createTime;
    private Date lastLoginTime;
    private String token;

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("appID", appID);
        json.put("channelID", channelID);
        json.put("name", name);
        json.put("channelUserID", channelUserID);
        json.put("channelUserName", channelUserName);
        json.put("channelUserNick", channelUserNick);
        return json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelUserID() {
        return channelUserID;
    }

    public void setChannelUserID(String channelUserID) {
        this.channelUserID = channelUserID;
    }

    public String getChannelUserName() {
        return channelUserName;
    }

    public void setChannelUserName(String channelUserName) {
        this.channelUserName = channelUserName;
    }

    public String getChannelUserNick() {
        return channelUserNick;
    }

    public void setChannelUserNick(String channelUserNick) {
        this.channelUserNick = channelUserNick;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

2、有了UUser对象之后，我们需要对该对象进行一些操作。首先就是数据访问，我们需要从数据库中获取到该对象对应的数据表，所以，我们在dao目录下新建一个UUserDao类，作为UUser对象的数据访问类。所有Dao数据访问类都继承UHibernateTemplate。这个抽象类有两个泛型参数。
第一个是当前数据访问类需要访问的数据对象，第二个参数是当前数据对象的主键类型

注意：所有数据访问类，都需要使用@Repository注解来声明，括号里面的名称为后面其他地方申明该类的对象时所必须使用的名称。

/**
 * 用户数据访问类
 */
@Repository("userDao")
public class UUserDao extends UHibernateTemplate<UUser, Integer>{

	//data operate

}

3、有了UUserDao，我们还需要在service目录下新建一个UUserManager作为UUser对象的业务操作类。

注意：所有业务操作类，都必须使用@Service注解来声明，括号里面的名称为后面其他地方申明该类的对象时所必须使用的名称。

/**
 * 这里处理UUser相关的逻辑操作
 */
@Service("userManager")
public class UUserManager {

    @Autowired		//使用Autowired注解来声明，采用Spring依赖注入来自动实例化该对象。
    private UUserDao userDao;	//这里变量的名称必须为userDao和上面@Repository声明该类时，指定的名称一致

    //根据渠道用户ID获取用户信息
    public UUser getUserByCpID(int appID, int channelID, String cpUserID){

        String hql = "from UUser where appID = ? and channelID = ? and channelUserID = ?";

        return (UUser)userDao.findUnique(hql, appID, channelID, cpUserID);

    }

    //获取指定渠道下的所有用户
    public List<UUser> getUsersByChannel(int channelID){
        String hql = "from UUser where channelID = ?";

        return userDao.find(hql, new Object[]{channelID}, null);
    }

    //分页查找
    public Page<UUser> queryPage(int currPage, int num){

        PageParameter page = new PageParameter(currPage, num, true);
        OrderParameters order = new OrderParameters();
        order.add("id", OrderParameter.OrderType.DESC);
        String hql = "from UUser";
        return userDao.find(page, hql, null, order);
    }


    public UUser getUser(int userID){

        return userDao.get(userID);
    }

    public void saveUser(UUser user){
        userDao.save(user);
    }

    public void deleteUser(UUser user){
        userDao.delete(user);
    }
}


4、有了UUser相关的数据对象和业务操作之后，我们需要一个控制器，来控制URL的访问，参数解析验证，调用业务层的逻辑进行处理，然后返回给客户端对应的结果。我们在web目录下，新建一个UserAction类。

注意：所有的Action类，都必须继承UActionSupport类。UActionSupport是common目录下，对struts2的一个简单的封装。
如果需要该Action采用数据驱动方式传递参数，那么还要实现ModelDriven接口。

建议，将同一个业务的方法写在一个Action类中。比如这里User对象的操作。增删改查等操作，都在该类中

http://localhost:8080/users/showUsers : 打开用户列表界面
http://localhost:8080/users/getAllUsers : 分页获取用户数据
http://localhost:8080/users/saveUser  : 增加或者编辑用户数据
http://localhost:8080/users/removeUser : 删除某个用户数据

@Controller		//所有Action都加上Controller注解
@Namespace("/users")	//根据业务区分Namespace，用Namespace主要防止命名冲突，并格式规范URL
public class UserAction extends UActionSupport implements ModelDriven<UUser>{


    private int page;           //当前请求的页码
    private int rows;           //当前每页显示的行数

    private UUser user;

    private int currUserID;

    @Autowired
    private UUserManager userManager;

    //http://localhost:8080/users/showUsers
    //访问这个地址，会访问到WEB-INF/admin/users.jsp
    @Action(value = "showUsers",
            results = {@Result(name = "success", location = "/WEB-INF/admin/users.jsp")})
    public String showUsers(){

        return "success";
    }

    //http://localhost:8080/users/getAllUsers
    @Action("getAllUsers")
    public void getAllUsers(){
        try{

            Page<UUser> currPage = this.userManager.queryPage(page, rows);

            JSONObject json = new JSONObject();
            json.put("total", currPage.getTotalCount());
            JSONArray users = new JSONArray();
            for(UUser m : currPage.getResultList()){
                users.add(m.toJSON());
            }
            json.put("rows", users);

            renderJson(json.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //添加或者编辑
    //http://localhost:8080/users/saveUser
    @Action("saveUser")
    public void saveUser(){
        try{

            userManager.saveUser(this.user);
            renderState(true);

            return;

        }catch(Exception e){
            e.printStackTrace();
        }

        renderState(false);
    }


    //http://localhost:8080/users/removeUser
    @Action("removeUser")
    public void removeUser(){
        try{
            Log.d("Curr userID is " + this.currUserID);

            UUser user = userManager.getUser(this.currUserID);

            if(user == null){
                renderState(false);
                return;
            }

            userManager.deleteUser(user);

            renderState(true);

            return;

        }catch(Exception e){
            e.printStackTrace();
        }

        renderState(false);
    }



    private void renderState(boolean suc){
        JSONObject json = new JSONObject();
        json.put("state", suc? 1 : 0);
        json.put("msg", suc? "操作成功" : "操作失败");
        renderText(json.toString());
    }


    @Override
    public UUser getModel() {
        if(this.user == null){
            this.user = new UUser();
        }
        return this.user;
    }

    public UUser getUser() {
        return user;
    }

    public void setUser(UUser user) {
        this.user = user;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCurrUserID() {
        return currUserID;
    }

    public void setCurrUserID(int currUserID) {
        this.currUserID = currUserID;
    }
}

开发SSH2项目，建议使用IntelliJ IDEA ，该框架也是基于该IDE开发的。如果是其他IDE，可能需要手动转换并重新配置下。

另外，欢迎大家访问我们的官方博客,并加入我们的技术群

官方博客：http://www.uustory.com
技术QQ群：207609068



