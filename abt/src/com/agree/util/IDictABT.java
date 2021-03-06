package com.agree.util;
/**
 * 由系统根据 网转平台数据字典V2.xls 自动生成，请勿做修改操作
 * @author chenqg
 *
 */
public interface IDictABT {
	
	/**
	 * 管理端渠道号
	 */
	String CHANNEL_BMS="TL110000";
	
	/**
	 * 手持端渠道号
	 */
	String CHANNEL_HOS="SS050500";
	/**
	 * 手持设备类型
	 */
	String DEVICE_TYPE_HOS="04";
	/**
	 * afa返回的正确时的状态码
	 */
	String AFARESULTSTATUS_SUCC = "000000";
	
	/**
	 * 预填单流水
	 */
	String BEFORE_SEQ = "before_seq";

	/**
	 * 网点号
	 */
	String BRANCH = "branch";

	/**
	 * 排队机IP
	 */
	String QM_IP = "qm_ip";
	
	/**
	 * 目标OID
	 */
	String OID = "oid";
	
	/**
	 * 发送消息的ID
	 */
	String MSGID  ="msgid";
	/**
	 * 填单类型
	 */
	String FORM_TYPE = "form_type";

	/**
	 * 填单内容
	 */
	String BEFORE_CONTENT = "before_content";

	/**
	 * 预填单编号
	 */
	String FM_NUM = "fm_num";

	/**
	 * 预填单名称
	 */
	String FM_NAME = "fm_name";

	/**
	 * 预填单IP
	 */
	String FM_IP = "fm_ip";

	/**
	 * 排队机编号 <br>
	 * 默认规则 1 到10 选择
	 */
	String QM_NUM = "qm_num";

	/**
	 * 预填单状态 <br>
	 * 1:启用 0:不启用
	 */
	String STATUS = "status";

	/**
	 * 日期
	 */
	String WORK_DATE = "work_date";

	/**
	 * 请求序号
	 */
	String REQUEST_SEQ = "request_seq";

	/**
	 * 请求渠道
	 */
	String REQUEST_CHANNEL = "request_channel";

	/**
	 * 卡号/账号
	 */
	String ACCOUNT_NO = "account_no";

	/**
	 * 预约编号
	 */
	String RESERV_ID = "reserv_id";

	/**
	 * 预约类别
	 */
	String RESERV_TYPE = "reserv_type";

	/**
	 * 预约登记时间
	 */
	String RESERV_RECORD_TIME = "reserv_record_time";

	/**
	 * 预约日期
	 */
	String RESERV_DATE = "reserv_date";

	/**
	 * 预约开始时间
	 */
	String RESERV_BEBIN_TIME = "reserv_bebin_time";

	/**
	 * 预约结束时间
	 */
	String RESERV_END_TIME = "reserv_end_time";

	/**
	 * 预约网点
	 */
	String RESERV_BRANCH = "reserv_branch";

	/**
	 * 预登记标志
	 */
	String RESERV_PRE_FLAG = "reserv_pre_flag";

	/**
	 * 预约状态 <br>
	 * 00-预约生效 01-预约取消 02-预约成功完成 03-预约异常完成
	 */
	String RESERV_STATE = "reserv_state";

	/**
	 * 修改时间
	 */
	String RESERV_MODIFY_TIME = "reserv_modify_time";

	/**
	 * 预约流水
	 */
	String RESERV_SEQ = "reserv_seq";

	/**
	 * 排队流水
	 */
	String QUEUE_SEQ = "queue_seq";

	/**
	 * 柜员服务流水
	 */
	String SERVICE_SEQ = "service_seq";

	/**
	 * 预约业务ID
	 */
	String RESERV_BUS_TYPE = "reserv_bus_type";

	/**
	 * 预约变更流水 （修改或取消预约）
	 */
	String RESERV_CHANGE_SEQ = "reserv_change_seq";

	/**
	 * 预约人手机号
	 */
	String PHONE_NO = "phone_no";

	/**
	 * 业务类型
	 */
	String BS_ID = "bs_id";

	/**
	 * 状态 <br>
	 * 00-可预约 01 不预约
	 */
	String RESERV_STATUS = "reserv_status";
	/**启用预约提前通知
	 * 0不提前 1提前
	 */
	String RESERV_ADVANCE_STATUS="reserv_advance_status";
	/**
	 * 预约最大人数
	 */
	String MAX_NUM = "max_num";

	/**
	 * 预约最大次数
	 */
	String MAX_TIME = "max_time";

	/**
	 * 手持设备标识
	 */
	String HANDDEVICE_ID = "handdevice_id";

	/**
	 * 终端号
	 */
	String TERM_NO = "term_no";

	/**
	 * 设备主管柜员
	 */
	String MANAGER = "manager";

	/**
	 * 分类ID
	 */
	String APP_ID = "app_id";

	/**
	 * 分类名
	 */
	String APP_NAME = "app_name";

	/**
	 * 上级分类ID
	 */
	String PARENT_ID = "parent_id";

	/**
	 * 分类级别
	 */
	String APP_LEVEL = "app_level";

	/**
	 * 顺序号 <br>
	 * 用于排序功能
	 */
	String LIST_ORDER = "list_order";

	/**
	 * 功能号
	 */
	String FUNC_ID = "func_id";

	/**
	 * 功能名
	 */
	String FUNC_NAME = "func_name";

	/**
	 * 功能路径
	 */
	String FUNC_LINK = "func_link";

	/**
	 * 索引号
	 */
	String INDEX_NUM = "index_num";

	/**
	 * 角色ID
	 */
	String ROLE_ID = "role_id";

	/**
	 * 角色名称
	 */
	String ROLE_NAME = "role_name";

	/**
	 * 创建日期
	 */
	String CREATE_DATE = "create_date";

	/**
	 * 创建人ID
	 */
	String CREATER_ID = "creater_id";

	/**
	 * 最后变更日期
	 */
	String LAST_MODIFY_DATE = "last_modify_date";

	/**
	 * 最后变更人ID
	 */
	String LAST_MODIFIER_ID = "last_modifier_id";

	/**
	 * 用户ID
	 */
	String USER_ID = "user_id";

	/**
	 * 口令
	 */
	String PASSWD = "passwd";

	/**
	 * 工号
	 */
	String WORK_ID = "work_id";

	/**
	 * 性别 <br>
	 * 1-男，2-女
	 */
	String USER_SEX = "user_sex";

	/**
	 * 出生年月
	 */
	String USER_BIRTH_DAY = "user_birth_day";

	/**
	 * 入职日期
	 */
	String START_WORK_DATE = "start_work_date";

	/**
	 * 联系电话
	 */
	String PHONE = "phone";

	/**
	 * 工作状态 <br>
	 * 0-正常, 1-实习，2-试用, 3-离职
	 */
	String WORK_STATE = "work_state";

	/**
	 * 用户状态 <br>
	 * 0-正常，1-冻结
	 */
	String STATE = "state";

	/**
	 * 密码错误次数
	 */
	String PASSWD_WRONG_TIME = "passwd_wrong_time";

	/**
	 * 登录时间
	 */
	String LOGIN_TIME = "login_time";

	/**
	 * 登录终端
	 */
	String LOGIN_TERM = "login_term";

	/**
	 * 机构名
	 */
	String BRANCH_NAME = "branch_name";

	/**
	 * <=3 Waiting time
	 */
	String LESS3 = "less3";

	/**
	 * 3<Waiting time <= 5
	 */
	String MORE3LESS5 = "more3less5";

	/**
	 * 5<Waiting time <= 10
	 */
	String MORE5LESS10 = "more5less10";

	/**
	 * 10<Waiting time <=15
	 */
	String MORE10LESS15 = "more10less15";

	/**
	 * 15<Waiting time <= 20
	 */
	String MORE15LESS20 = "more15less20";

	/**
	 * 20<Waiting time <= 30
	 */
	String MORE20LESS30 = "more20less30";

	/**
	 * 30<Waiting time
	 */
	String MORE30 = "more30";

	/**
	 * 等待时间合计
	 */
	String TOTAL_WAITING_TIME = "total_waiting_time";

	/**
	 * 平均等待时间
	 */
	String AVERAGE_WAITING_TIME = "average_waiting_time";

	/**
	 * 柜员号
	 */
	String TELLER = "teller";

	/**
	 * 柜员名
	 */
	String TELLER_NAME = "teller_name";

	/**
	 * 有效服务人数
	 */
	String PEOPLE_COUNT = "people_count";

	/**
	 * 平均服务时间
	 */
	String AVERAGE_SERVICE_TIME = "average_service_time";

	/**
	 * 平均客户用时
	 */
	String AVERAGE_USE_TIME = "average_use_time";

	/**
	 * 有效时间率
	 */
	String TIME_EFFICIENCY = "time_efficiency";

	/**
	 * 总叫号人数
	 */
	String TOTAL_CALL_NUM = "total_call_num";

	/**
	 * 总服务人数
	 */
	String TOTAL_SERVICE_NUM = "total_service_num";

	/**
	 * 总评价人数
	 */
	String TOTAL_ASSESS_NUM = "total_assess_num";

	/**
	 * 非常满意
	 */
	String VERY_SATISFY = "very_satisfy";

	/**
	 * 基本满意
	 */
	String SATISFY = "satisfy";

	/**
	 * 不满意
	 */
	String NOT_SATISFY = "not_satisfy";

	/**
	 * 评价率
	 */
	String ASSESS_RATE = "assess_rate";

	/**
	 * 满意率
	 */
	String VERY_SATISFY_RATE = "very_satisfy_rate";

	/**
	 * 一般率
	 */
	String SATISFY_RATE = "satisfy_rate";

	/**
	 * 不满意率
	 */
	String NOT_SATISFY_RATE = "not_satisfy_rate";

	/**
	 * 排队号码
	 */
	String QUEUE_NO = "queue_no";

	/**
	 * 取号时间
	 */
	String EN_QUEUE_TIME = "en_queue_time";

	/**
	 * 叫号时间
	 */
	String CALL_TIME = "call_time";

	/**
	 * 评价时间
	 */
	String ASSESS_TIME = "assess_time";

	/**
	 * 等待时长
	 */
	String WAITING_TIME = "waiting_time";

	/**
	 * 服务时长
	 */
	String SERVICE_TIME = "service_time";

	/**
	 * 评价类型
	 */
	String ASSESS_RESULT = "assess_result";

	/**
	 * 业务ID
	 */
	String QUEUE_BUS_TYPE = "queue_bus_type";

	/**
	 * 类型名称
	 */
	String BUS_TYPE_NAME = "bus_type_name";

	/**
	 * 办理人数
	 */
	String DOBUS_COUNT = "dobus_count";

	/**
	 * 8点以前
	 */
	String LESS_8 = "less_8";

	/**
	 * 8-9点
	 */
	String T8_9 = "t8_9";

	/**
	 * 9-10点
	 */
	String T9_10 = "t9_10";

	/**
	 * 10-11点
	 */
	String T10_11 = "t10_11";

	/**
	 * 11-12点
	 */
	String T11_12 = "t11_12";

	/**
	 * 12-13点
	 */
	String T12_13 = "t12_13";

	/**
	 * 13-14点
	 */
	String T13_14 = "t13_14";

	/**
	 * 14-15点
	 */
	String T14_15 = "t14_15";

	/**
	 * 15-16点
	 */
	String T15_16 = "t15_16";

	/**
	 * 16-17点
	 */
	String T16_17 = "t16_17";

	/**
	 * 17-18点
	 */
	String T17_18 = "t17_18";

	/**
	 * 18点以后
	 */
	String MORE_18 = "more_18";

	/**
	 * 合计
	 */
	String TOTAL = "total";

	/**
	 * 消息流水号
	 */
	String MSG_ID = "msg_id";

	/**
	 * 渠道标识
	 */
	String CHANNEL_ID = "channel_id";

	/**
	 * 序列号 <br>
	 * 请求渠道自己生成
	 */
	String CHANNEL_SEQ = "channel_seq";

	/**
	 * 渠道日期
	 */
	String CHANNEL_DATE = "channel_date";

	/**
	 * 渠道时间
	 */
	String CHANNEL_TIME = "channel_time";

	/**
	 * 服务ID
	 */
	String SERVICE_ID = "service_id";

	/**
	 * 平台机器标识
	 */
	String BT_MACHINE_ID = "bt_machine_id";

	/**
	 * 日志文件名 <br>
	 * 多个日志文件用|分隔
	 */
	String LOG_FILE_NAME = "log_file_name";

	/**
	 * 请求到达时间
	 */
	String REQ_TIMESTAMP = "req_timestamp";

	/**
	 * 请求回应时间
	 */
	String RSP_TIMESTAMP = "rsp_timestamp";

	/**
	 * 处理返回状态
	 */
	String RET_STATUS = "ret_status";

	/**
	 * 处理返回代码
	 */
	String RET_CODE = "ret_code";

	/**
	 * 返回描述
	 */
	String RET_DESC = "ret_desc";

	/**
	 * 系统标识 <br>
	 * 规范 BT_001：智能排队机 BT_002：预约平台 BT_003：网点管理 PRIMARY KEY
	 */
	String SYS_ID = "sys_id";

	/**
	 * 系统名
	 */
	String SYS_NAME = "sys_name";

	/**
	 * 系统状态 <br>
	 * 00-停用 01-运行
	 */
	String SYS_STATUS = "sys_status";

	/**
	 * 渠道命名
	 */
	String CHANNEL_NAME = "channel_name";

	/**
	 * IP地址
	 */
	String IP = "ip";

	/**
	 * FTP端口号
	 */
	String PORT_NUM = "port_num";

	/**
	 * 超时时间
	 */
	String TIMEOUT = "timeout";

	/**
	 * FTP服务IP
	 */
	String FILE_SERVER_IP = "file_server_ip";

	/**
	 * 文件目录
	 */
	String FILE_DIR = "file_dir";

	/**
	 * FTP用户
	 */
	String USER = "user";

	/**
	 * 机构地址
	 */
	String ADDRESS = "address";

	/**
	 * gps经度
	 */
	String LONGITUDE = "longitude";

	/**
	 * gps纬度 <br>
	 * 如’720,850'
	 */
	String LATITUDE = "latitude";

	/**
	 * 行所级别 <br>
	 * 0-总行、1-分行、3-网点
	 */
	String BANK_LEVEL = "bank_level";

	/**
	 * 所属机构
	 */
	String SUBBANK_NUM = "subbank_num";

	/**
	 * 附近车站
	 */
	String NEAR_STATION = "near_station";

	/**
	 * 网点电话
	 */
	String BANK_TEL = "bank_tel";

	/**
	 * 负责人
	 */
	String MANAGER_NAME = "manager_name";

	/**
	 * 负责人手机
	 */
	String MANAGER_PHONE = "manager_phone";

	/**
	 * 负责人电话
	 */
	String MANAGER_TEL = "manager_tel";

	/**
	 * 备注
	 */
	String NOTE = "note";

	/**
	 * 机构专属校验码
	 */
	String CHECKCODE = "checkcode";

	/**
	 * 国标区域码
	 */
	String REGION_CODE = "region_code";

	/**
	 * 区域名
	 */
	String REGION_NAME = "region_name";

	/**
	 * 分行号
	 */
	String SUBBANK = "subbank";

	/**
	 * 临近网点号 <br>
	 * 对应维护各网点的临近网点信息各临近网点号用”|”分割
	 */
	String BRANCH_NEIGHBOUS = "branch_neighbous";

	/**
	 * 节假日序号
	 */
	String HOLIDAY_ID = "holiday_id";

	/**
	 * 节假日名称
	 */
	String HOLIDAY_NAME = "holiday_name";

	/**
	 * 起始日期 <br>
	 * yyyy-MM-dd
	 */
	String START_DATE = "start_date";

	/**
	 * 结束日期 <br>
	 * yyyy-MM-dd
	 */
	String END_DATE = "end_date";

	/**
	 * 目标IP
	 */
	String TARGET_IP = "target_ip";

	/**
	 * 用户
	 */
	String USER_NAME = "user_name";

	/**
	 * 机构号
	 */
	String BRANCH_NO = "branch_no";

	/**
	 * URL
	 */
	String URL = "url";

	/**
	 * 描述
	 */
	String SERVICE_DESC = "service_desc";

	/**
	 * 服务模式 <br>
	 * 00-一般联机 01-7*24联机 02-批量
	 */
	String SERVICE_MODE = "service_mode";

	/**
	 * 服务状态 <br>
	 * 00-正常 01-停止
	 */
	String SERVICE_STATUS = "service_status";

	/**
	 * 模块代码 <br>
	 * 对应afa模板码
	 */
	String MOD_CODE = "mod_code";

	/**
	 * 功能码 <br>
	 * 对应afa交易码
	 */
	String TRADE_CODE = "trade_code";

	/**
	 * 接口检查标志 <br>
	 * 00- 不检查01- 检查
	 */
	String INTERFACE_CHECK_FLAG = "interface_check_flag";

	/**
	 * 接口关键字
	 */
	String INTERFACE_KEY = "interface_key";

	/**
	 * 转发标志 <br>
	 * 00- 非转发01- 转发
	 */
	String TRANS_FLAG = "trans_flag";

	/**
	 * 转发目的渠道标识 <br>
	 * 00- 一般联机01- 7*24联机02- 批量
	 */
	String TRANS_DEST_CHANNEL = "trans_dest_channel";

	/**
	 * 网点标志 <br>
	 * 00-不填 01-填
	 */
	String BRANCH_FLAG = "branch_flag";

	/**
	 * 默认网点
	 */
	String DEFAULT_BRANCH = "default_branch";

	/**
	 * 默认柜员
	 */
	String DEFAULT_TELLER = "default_teller";

	/**
	 * 参数代码
	 */
	String CODE = "code";

	/**
	 * 参数名称
	 */
	String NAME = "name";

	/**
	 * 参数值
	 */
	String VALUE = "value";

	/**
	 * 是否允许通用修改 <br>
	 * 0-不允许，1-允许
	 */
	String REVISABLE = "revisable";

	/**
	 * 步骤序号
	 */
	String STEP = "step";

	/**
	 * 处理状态 <br>
	 * 0 成功 1 处理未完成 2 调用有误
	 */
	String BATCH_STATUS = "batch_status";

	/**
	 * 处理结果描述
	 */
	String TRACK_DESC = "track_desc";

	/**
	 * 批量处理次数
	 */
	String EXETIMES = "exetimes";

	/**
	 * 批量开始时间
	 */
	String STARTTIME = "starttime";

	/**
	 * 批量结束时间
	 */
	String ENDTIME = "endtime";

	/**
	 * 错误描述
	 */
	String RET_MSG = "ret_msg";

	/**
	 * 待转换渠道 <br>
	 * 即把A渠道错误码转换成B渠道错误码
	 */
	String CHANNEL_ID_A = "channel_id_a";

	/**
	 * 待转换错误码
	 */
	String RET_CODE_A = "ret_code_a";

	/**
	 * 转换后渠道
	 */
	String CHANNEL_ID_B = "channel_id_b";

	/**
	 * 转换后错误码
	 */
	String RET_CODE_B = "ret_code_b";

	/**
	 * 队列号 <br>
	 * 前缀起始位三位队列数001开始递增
	 */
	String QUEUE_NUM = "queue_num";

	/**
	 * 转移次数 <br>
	 * 未转移号码为0每转移一次递增1 从而生成多个相同排队号
	 */
	String TRANSFER_NUM = "transfer_num";

	/**
	 * 服务实体柜员号
	 */
	String SOFTCALL_TELLER = "softcall_teller";

	/**
	 * 服务实体柜员名
	 */
	String SOFTCALL_TELLER_NAME = "softcall_teller_name";

	/**
	 * 软叫号流水
	 */
	String SOFTCALL_SEQ = "softcall_seq";

	/**
	 * 节点ID <br>
	 * 排队机界面的最终业务节点
	 */
	String NODE_ID = "node_id";

	/**
	 * 排队号类型 <br>
	 * 1-普通2-转移3-预约
	 */
	String QUEUENUM_TYPE = "queuenum_type";

	/**
	 * 排队状态 <br>
	 * 00-取号 01-业务办理中 02-业务办理结束 03-弃号 04-转移
	 */
	String QUEUE_STATUS = "queue_status";

	/**
	 * 出队时间 <br>
	 * HHMMSS
	 */
	String DE_QUEUE_TIME = "de_queue_time";

	/**
	 * 开始办理业务时间 <br>
	 * HHMMSS
	 */
	String START_SERV_TIME = "start_serv_time";

	/**
	 * 结束办理业务时间 <br>
	 * HHMMSS
	 */
	String FINISH_SERV_TIME = "finish_serv_time";

	/**
	 * 客户评价结果 <br>
	 * 1不满意 2一般 3满意 4非常满意
	 */
	String ASSESS_STATUS = "assess_status";

	/**
	 * 预约标志 <br>
	 * 0-无 1-有
	 */
	String RESERV_FLAG = "reserv_flag";

	/**
	 * 提醒标志 <br>
	 * 是否进行短信提醒 0否 1是
	 */
	String REMAIND_FLAG = "remaind_flag";

	/**
	 * 手机号
	 */
	String REMAIND_PHONE = "remaind_phone";

	/**
	 * 前面的等待人数
	 */
	String NOTI_WAITNUM = "noti_waitnum";

	/**
	 * 是否已经通知 <br>
	 * 0 未通知，1 已通知
	 */
	String ISNOTIFY = "isnotify";

	/**
	 * 是否预填单 <br>
	 * 0否 1是
	 */
	String ISBEFORE = "isbefore";

	/**
	 * 填单状态 <br>
	 * 0-未完成 1-已完成 2-取消 3-正在处理 4-处理完毕
	 */
	String BEFORESTATUS = "beforestatus";

	/**
	 * 处理窗口IP <br>
	 * 统计分析用
	 */
	String WIN_IP = "win_ip";

	/**
	 * 设定的通知等待人数
	 */
	String NOTI_SETNUM = "noti_setnum";

	/**
	 * 业务名称(中文)
	 */
	String BS_NAME_CH = "bs_name_ch";

	/**
	 * 业务名称(英文)
	 */
	String BS_NAME_EN = "bs_name_en";

	/**
	 * 所属业务类别 <br>
	 * 0个人业务，1对公业务
	 */
	String BS_TYPE = "bs_type";

	/**
	 * 机构类型 <br>
	 * 1-总行 2-分行';3-支行4-网点
	 */
	String BRANCH_LEVEL = "branch_level";

	/**
	 * 刷卡标志 <br>
	 * 0-不刷 1-可刷可不刷 2-必刷
	 */
	String CARD_FLAG = "card_flag";

	/**
	 * 业务签到状态 <br>
	 * 预留字段，用于业务动态控制 0 未签到 1 已签到
	 */
	String BS_SIGNSTATUS = "bs_signstatus";

	/**
	 * 业务服务时间编号
	 */
	String BS_SERVICETIME_ID = "bs_servicetime_id";

	/**
	 * 是否默认标志 <br>
	 * 0-默认模板 1-非默认
	 */
	String DEFAULT_FLAG = "default_flag";

	/**
	 * 星期
	 */
	String WEEK = "week";

	/**
	 * 服务开始时间
	 */
	String SS_TIME = "ss_time";

	/**
	 * 服务结束时间
	 */
	String SE_TIME = "se_time";

	/**
	 * 提前取号时间 <br>
	 * 分钟数
	 */
	String AT_TIME = "at_time";

	/**
	 * 排队机名称
	 */
	String QM_NAME = "qm_name";

	/**
	 * 小票模板编号
	 */
	String TICKET_STYLE_ID = "ticket_style_id";

	/**
	 * 模板内容
	 */
	String STYLE_CONTENT = "style_content";

	/**
	 * 窗口号
	 */
	String WIN_NUM = "win_num";

	/**
	 * ABC OID
	 */
	String WIN_OID = "win_oid";

	/**
	 * 叫号规则 <br>
	 * 1-静态规则,2-动态规则
	 */
	String CALL_RULE = "call_rule";

	/**
	 * 条屏样式编号
	 */
	String SCREEN_ID = "screen_id";

	/**
	 * 软叫号配置编号
	 */
	String SOFTCALL_ID = "softcall_id";

	/**
	 * 评价配置ID
	 */
	String ASSESS_ID = "assess_id";

	/**
	 * 柜台服务状态 <br>
	 * 0 离线 1 空闲 2 正在办理 3 暂停服务
	 */
	String WIN_SERVICE_STATUS = "win_service_status";

	/**
	 * 音箱编号
	 */
	String SOUNDBOX_ID = "soundbox_id";
	
	/**
	 * 无线音箱IP
	 */
	String SB_IP = "sb_ip";

	/**
	 * 排队机参数表ID <br>
	 * 自增
	 */
	String QM_PARAM_ID = "qm_param_id";

	/**
	 * 定时关机时间 <br>
	 * 0表示不启用定时关机
	 */
	String TIMEING_SHUTDOWN = "timeing_shutdown";

	/**
	 * 排队机设置密码
	 */
	String CFG_PWD = "cfg_pwd";

	/**
	 * 是否显示时钟 <br>
	 * 0不显示，1显示
	 */
	String SHOW_CLOCK = "show_clock";

	/**
	 * 是否短信通知客户 <br>
	 * 0禁用，1启用
	 */
	String SMS_CUSTOMER = "sms_customer";

	/**
	 * 语音呼叫设置 <br>
	 * 0 普通话，1 英语，2粤语
	 */
	String CALL_VOICE = "call_voice";

	/**
	 * 是否支持预约 <br>
	 * 0 不支持，1 支持
	 */
	String ISPRECONTRACT = "isprecontract";

	/**
	 * 是否显示临近网点 <br>
	 * 0不显示，1显示
	 */
	String NEAR_BRANCH = "near_branch";

	/**
	 * 是否显示临近网点排队信息 <br>
	 * 0不显示，1显示
	 */
	String NEAR_BRANCH_WAIT_NUM = "near_branch_wait_num";

	/**
	 * 卡名称
	 */
	String CARD_NAME = "card_name";

	/**
	 * 卡类别
	 */
	String CARD_TYPE = "card_type";

	/**
	 * 识别顺序
	 */
	String CHECK_LEVEL = "check_level";

	/**
	 * 磁道号
	 */
	String TRACK_NO = "track_no";

	/**
	 * 数据位置-始
	 */
	String DATA_START = "data_start";

	/**
	 * 数据位置-终
	 */
	String DATA_END = "data_end";

	/**
	 * 卡bin验证-始
	 */
	String BIN_START = "bin_start";

	/**
	 * 卡bin验证-终
	 */
	String BIN_END = "bin_end";

	/**
	 * 卡bin验证数据
	 */
	String BIN_CHECK = "bin_check";

	/**
	 * VIP卡BIN验证-始
	 */
	String VIP_BIN_START = "vip_bin_start";

	/**
	 * VIP卡BIN验证-终
	 */
	String VIP_BIN_END = "vip_bin_end";

	/**
	 * VIP卡BIN验证数据
	 */
	String VIP_BIN_CHECK = "vip_bin_check";

	/**
	 * 窗口空闲时显示内容
	 */
	String FREE_CONTENT = "free_content";

	/**
	 * 窗口空闲时显示效果
	 */
	String FREE_STYLE = "free_style";

	/**
	 * 窗口空闲时显示速度
	 */
	String FREE_SPEED = "free_speed";

	/**
	 * 窗口叫号时显示内容
	 */
	String CALL_CONTENT = "call_content";

	/**
	 * 窗口叫号时显示效果
	 */
	String CALL_STYLE = "call_style";

	/**
	 * 窗口叫号时显示速度
	 */
	String CALL_SPEED = "call_speed";

	/**
	 * 窗口暂停时显示内容
	 */
	String PAUSE_CONTENT = "pause_content";

	/**
	 * 窗口暂停时显示效果
	 */
	String PAUSE_STYLE = "pause_style";

	/**
	 * 窗口暂停时显示速度
	 */
	String PAUSE_SPEED = "pause_speed";

	/**
	 * 窗口正在办理时显示内容
	 */
	String SERVE_CONTENT = "serve_content";

	/**
	 * 窗口正在办理时显示效果
	 */
	String SERVE_STYLE = "serve_style";

	/**
	 * 窗口正在办理时显示速度
	 */
	String SERVE_SPEED = "serve_speed";

	/**
	 * 重呼限制时间
	 */
	String RECALL_LIMIT = "recall_limit";

	/**
	 * 顺呼限制时间
	 */
	String CALL_NEXT_LIMIT = "call_next_limit";

	/**
	 * 通知等待客户顺位
	 */
	String NOTIFY_CUSTOMER = "notify_customer";

	/**
	 * 主题ID <br>
	 * 自定生成
	 */
	String THEME_ID = "theme_id";

	/**
	 * 主题名称
	 */
	String THEME_NAME = "theme_name";

	/**
	 * 主题背景图片
	 */
	String BGD_IMG = "bgd_img";

	/**
	 * 背景图片数据
	 */
	String BGD_DATA = "bgd_data";

	/**
	 * 按钮背景图片
	 */
	String BUTTON_IMG = "button_img";

	/**
	 * 按钮图片数据
	 */
	String BUTTON_DATA = "button_data";

	/**
	 * 字体名称
	 */
	String TEXT_FONT = "text_font";

	/**
	 * 字体类型
	 */
	String TEXT_STYLE = "text_style";

	/**
	 * 字体大小
	 */
	String TEXT_SIZE = "text_size";

	/**
	 * 字体颜色
	 */
	String TEXT_COLOR = "text_color";

	/**
	 * 开始时间
	 */
	String START_TIME = "start_time";

	/**
	 * 结束时间
	 */
	String END_TIME = "end_time";

	/**
	 * 证件类型 <br>
	 * 唯一标识客户的证件类型:0 身份证，1银行卡卡号，2客户号，3护照号码
	 */
	String CUSTINFO_TYPE = "custinfo_type";

	/**
	 * 证件号码 <br>
	 * 唯一标识客户的证件号码
	 */
	String CUSTINFO_NUM = "custinfo_num";

	/**
	 * 客户姓名
	 */
	String CUSTINFO_NAME = "custinfo_name";

	/**
	 * 客户性别 <br>
	 * 1男 2女 0未知 9 未说明性别
	 */
	String CUSTINFO_SEX = "custinfo_sex";

	/**
	 * 身份证号码 <br>
	 * 身份证号码
	 */
	String ID_NUM = "id_num";

	/**
	 * 护照号码 <br>
	 * 护照号码
	 */
	String PASSPORT_NUM = "passport_num";

	/**
	 * 客户号（浦发） <br>
	 * 客户号（浦发）
	 */
	String CUSTOMER_NUM = "customer_num";

	/**
	 * 持有银行卡卡号 <br>
	 * 持有银行卡卡号
	 */
	String CARD_NUM = "card_num";

	/**
	 * 持有存折账号 <br>
	 * 持有存折账号
	 */
	String DEPOSIT_NUM = "deposit_num";

	/**
	 * 资产信息 <br>
	 * 1：未知，大于0：实际资产信息
	 */
	String ASSET_INFORMATION = "asset_information";

	/**
	 * 理财三月平均余额 <br>
	 * 理财三月平均余额
	 */
	String AVG_BALANCE = "avg_balance";

	/**
	 * 昨日资产余额 <br>
	 * 昨日资产余额
	 */
	String YESTERDAY_BALANCE = "yesterday_balance";

	/**
	 * 业务开通情况 <br>
	 * 以’|’分割
	 */
	String BS_CASE = "bs_case";

	/**
	 * 智能营销话语
	 */
	String MARKETING_CONTENT = "marketing_content";

	/**
	 * 内部客户类型
	 */
	String CUSTTYPE_I = "custtype_i";

	/**
	 * 外部客户类型 <br>
	 * 第三方系统返回的客户类型
	 */
	String CUSTTYPE_E = "custtype_e";

	/**
	 * 客户类型名称 <br>
	 * 客户类型名称
	 */
	String CUSTTYPE_NAME = "custtype_name";

	/**
	 * 客户类型级别 <br>
	 * 第三方系统级别，用于级别调整
	 */
	String LEVEL = "level";

	/**
	 * 是否VIP <br>
	 * 0否，1是
	 */
	String ISVIP = "isvip";

	/**
	 * 小票键值 <br>
	 * 预留字段，用于小票显示客户类型对应的提示
	 */
	String TICKET_KEY = "ticket_key";

	/**
	 * 队列类型ID
	 */
	String QUEUETYPE_ID = "queuetype_id";

	/**
	 * 队列名称
	 */
	String QUEUETYPE_NAME = "queuetype_name";

	/**
	 * 队列前缀字母 <br>
	 * 便于快速检索所需业务
	 */
	String QUEUETYPE_PREFIX = "queuetype_prefix";

	/**
	 * 队列前缀数字 <br>
	 * 便于快速检索所需业务
	 */
	String QUEUETYPE_PREFIX_NUM = "queuetype_prefix_num";

	/**
	 * 队列权重（优先级）
	 */
	String QUEUETYPE_LEVEL = "queuetype_level";

	/**
	 * 节点层级 <br>
	 * 节点所在层级
	 */
	String NODE_LEVEL = "node_level";

	/**
	 * 节点位置 <br>
	 * 同级节点位置
	 */
	String NODE_LOCATION = "node_location";

	/**
	 * 子节点顺序 <br>
	 * 以’@’分隔子节点ID，按顺序存储
	 */
	String CHILD_NODE_ORDER = "child_node_order";

	/**
	 * 节点类型 <br>
	 * 0：目录，1：业务
	 */
	String NODE_TYPE = "node_type";

	/**
	 * 目录名称 <br>
	 * 类型为业务的时候，该字段为空
	 */
	String DIR_NAME = "dir_name";

	/**
	 * 默认关联客户类型 <br>
	 * 参照BT_CUSOMTER_TYPE的External_customer_type字段
	 */
	String DEFAULT_CUSTYPE = "default_custype";

	/**
	 * 默认队列
	 */
	String DEAFULT_QUEUE = "deafult_queue";

	/**
	 * 客户查找规则 <br>
	 * 0：刷卡优先，1自身优先
	 */
	String CUSTOMER_LOOKUP_RULE = "customer_lookup_rule";

	/**
	 * 目录：是否显示该目录下等待人数 <br>
	 * 0：不显示，1：显示
	 */
	String SHOW_WAIT_NUM = "show_wait_num";

	/**
	 * 等待区域 <br>
	 * 等待区域描述
	 */
	String WAIT_AREA = "wait_area";

	/**
	 * 是否显示提示信息 <br>
	 * 0:不显示, 1:显示
	 */
	String IS_SHOW_MSG = "is_show_msg";

	/**
	 * 提示信息 <br>
	 * 选择该业务后显示的提示信息
	 */
	String PROMPT_MSG = "prompt_msg";

	/**
	 * 评价超时时间
	 */
	String ASSESS_OUTT = "assess_outt";

	/**
	 * 余额输入超时时间
	 */
	String MONEY_IN_OUTT = "money_in_outt";

	/**
	 * 密码修改输入超时时间
	 */
	String PASS_IN_OUTT = "pass_in_outt";

	/**
	 * 交互信息展示超时时间
	 */
	String MSG_IN_OUTT = "msg_in_outt";

	/**
	 * 业务预警人数
	 */
	String BS_WAIT_NUM = "bs_wait_num";

	/**
	 * 功能标识位
	 */
	String FLAG = "flag";

	/**
	 * 图片名称
	 */
	String IMGNAME = "imgname";

	/**
	 * 图片所属产品级别
	 */
	String IMGLEVEL = "imglevel";

	/**
	 * 图片数据
	 */
	String IMGDATA = "imgdata";

	/**
	 * 最后修改时间
	 */
	String LASTMODIFIED = "lastmodified";

	/**
	 * 文档索引
	 */
	String DOCID = "docid";

	/**
	 * 文档编号
	 */
	String DOCNUM = "docnum";

	/**
	 * 文档名称
	 */
	String DOCNAME = "docname";

	/**
	 * 文档级别
	 */
	String DOCLEVEL = "doclevel";

	/**
	 * 后缀名
	 */
	String EXTENSION = "extension";

	/**
	 * 文档数据
	 */
	String DOCDATA = "docdata";

	/**
	 * 文档文本
	 */
	String DOCTEXT = "doctext";

	/**
	 * 时间
	 */
	String WORK_TIME = "work_time";

	/**
	 * 客户端标识
	 */
	String CLIENT_ID = "client_id";

	/**
	 * 消息状态 <br>
	 * 0 未读 1已读 2 已删除
	 */
	String MSG_STATUS = "msg_status";

	/**
	 * 消息类型
	 */
	String MSG_TYPE = "msg_type";

	/**
	 * 消息内容
	 */
	String MSG_CONTENT = "msg_content";

	/**
	 * 产品索引
	 */
	String PRODUCTID = "productid";

	/**
	 * 产品编号
	 */
	String PRODUCTNUM = "productnum";

	/**
	 * 产品名称
	 */
	String PRODUCTNAME = "productname";

	/**
	 * 产品级别
	 */
	String PRODUCTLEVEL = "productlevel";

	/**
	 * 产品数据
	 */
	String PRODUCTDATA = "productdata";

	/**
	 * 产品文本
	 */
	String PRODUCTTEXT = "producttext";

	/**
	 * 工具索引
	 */
	String TOOLID = "toolid";

	/**
	 * 工具名称
	 */
	String TOOLNAME = "toolname";

	/**
	 * 工具数据
	 */
	String TOOLDATA = "tooldata";

	/**
	 * 业务ID列表
	 */
	String BS_IDS = "bs_ids";

	/**
	 * 端口号
	 */
	String PORT = "port";

	/**
	 * 是否启用业务服务时间 <br>
	 * 0禁用，1启用
	 */
	String BS_SERVICETIME_STATUS = "bs_servicetime_status";

	/**
	 * 是否启用主题 <br>
	 * 0禁用，2启用
	 */
	String THEME_STATUS = "theme_status";

	/**
	 * 端口号 <br>
	 * 0禁用，3启用
	 */
	String SOUNDBOX_STATUS = "soundbox_status";

	/**
	 * 消费方系统编号 <br>
	 * 为消费方系统唯一分配的一个系统编号；
	 */
	String M_CUSTOMERNO = "M_CustomerNo";

	/**
	 * 报文类型 <br>
	 * NATP 或者 XML
	 */
	String M_PACKAGETYPE = "M_PackageType";

	/**
	 * 服务方系统编号 <br>
	 * 服务模块代码，理解为MC,默认为UPBS
	 */
	String M_SERVICERNO = "M_ServicerNo";

	/**
	 * 服务代码 <br>
	 * 唯一标识服务方的一个功能代码，理解为TC
	 */
	String M_SERVICECODE = "M_ServiceCode";

	/**
	 * 报文发起日期 <br>
	 * 报文发送的日期
	 */
	String M_MESGSNDDATE = "M_MesgSndDate";

	/**
	 * 报文发起时间 <br>
	 * 标识报文原始业务发起方的渠道时间；
	 */
	String M_MESGSNDTIME = "M_MesgSndTime";

	/**
	 * 报文消息ID <br>
	 * 通信层标识一个报文； 顺序编制，并确保在当日唯一；
	 * 接收方根据“消费方系统编号”＋“报文发起日期”＋“报文消息ID”唯一确定一个报文，该三项重复的报文作为通信级重复报文； 标识本报文的关联报文
	 */
	String M_MESGID = "M_MesgId";

	/**
	 * 报文消息参考号 <br>
	 * 标识本报文的关联报文
	 */
	String M_MESGREFID = "M_MesgRefId";

	/**
	 * 报文优先级 <br>
	 * 1：特急； 2：紧急； 3：普通；
	 */
	String M_MESGPRIORITY = "M_MesgPriority";

	/**
	 * 报文方向 <br>
	 * 1：请求 2：应答
	 */
	String M_DIRECTION = "M_Direction";

	/**
	 * 调用方式 <br>
	 * 1：同步调用 2：异步调用
	 */
	String M_CALLMETHOD = "M_CallMethod";

	/**
	 * （保留域） <br>
	 * 保留。
	 */
	String M_RESERVE = "M_Reserve";

	/**
	 * 渠道代码 <br>
	 * 发起请求的渠道代码 01 排队机 02 软叫号 03 网点管理系统 04 手持设备 05 手机银行 06 贵宾系统 07 网银系统 08 短信平台
	 */
	String H_CHANNELCODE = "H_channelcode";

	/**
	 * 渠道日期 <br>
	 * 发起请求的渠道日期
	 */
	String H_CHANNELDATE = "H_channeldate";

	/**
	 * 渠道代码 <br>
	 * 发起请求的渠道时间
	 */
	String H_CHANNELTIME = "H_channeltime";

	/**
	 * 渠道流水号 <br>
	 * 发起请求的渠道流水号，长度取决于渠道，最长30位
	 */
	String H_CHANNELSERNO = "H_channelserno";

	/**
	 * 机构码 <br>
	 * 发起请求的机构
	 */
	String H_BRNO = "H_brno";

	/**
	 * 柜员号 <br>
	 * 发起请求的柜员，可以是录入柜员、复核柜员、授权柜员等
	 */
	String H_TELLERNO = "H_tellerno";

	/**
	 * 分页标识 <br>
	 * 1-最后一页 2-上一页 3-下一页 4-当前页 转入到平台内部要为字典_opertype_
	 */
	String H_PAGEFLAG = "H_pageflag";

	/**
	 * 当前页码 <br>
	 * 转入到平台内部为字典_currpage_
	 */
	String H_CURRPAGE = "H_currpage";

	/**
	 * 每页记录数 <br>
	 * 转入到平台内部为_pagenum_
	 */
	String H_PAGENUM = "H_pagenum";

	/**
	 * 处理状态 <br>
	 * 交易处理状态S：成功，F：失败
	 */
	String H_RET_STATUS = "H_ret_status";

	/**
	 * 处理返回码 <br>
	 * 交易处理码
	 */
	String H_RET_CODE = "H_ret_code";

	/**
	 * 处理返回描述 <br>
	 * 交易处理信息
	 */
	String H_RET_DESC = "H_ret_desc";

	/**
	 * 总记录数 <br>
	 * 满足条件的总记录数
	 */
	String H_TOTALROWNUM = "H_totalrownum";

	/**
	 * 总页数 <br>
	 * 满足条件的页总数
	 */
	String H_TOTALPAGENUM = "H_totalpagenum";

	/**
	 * 当前页 <br>
	 * 当前页码
	 */
	String H_NOWPAGENUM = "H_nowpagenum";

	/**
	 * 每页记录数 <br>
	 * 每页记录数
	 */
	String H_PAGEROWNUM = "H_pagerownum";

	/**
	 * 注册交易码<br>
	 * 注册交易码，以逗号分隔
	 */
	String REGISTTRADE = "registtrade";

	/**
	 * 客户类型级别<br>
	 * 第三方系统级别，用于级别调整
	 */
	String CUSTTYPE_LEVEL = "custtype_level";
	
	/**
	 * 是否显示业务等待人数<br>
	 * 0不显示，1显示,DEFAULT 1
	 */
	String SHOW_BS_WAITNUM = "show_bs_waitnum";
	
	/**
	 * 是否显示目录等待人数<br>
	 * 0不显示，1显示,DEFAULT 1
	 */
	String SHOW_DIR_WAITNUM = "show_dir_waitnum";
	String APPLY_HOLIDAY = "apply_holiday";
	
	/**
	 * 模板配置路径清单
	 * machineView 排队机界面  
	 * queueingRules 叫号规则
	 * qm 排队机
	 * window 窗口
	 * managerCallNum 大堂经理优先队列
	 * branchParam 机构参数配置
	 */
	String[] MODEL_LIST = {"machineView","queueingRules","qm","window","managerCallNum","branchParam"};
	
	/** 预填单参数变量名 **/
	String TRADE_ID = "trade_id";	//交易页面ID
	String TRADE_NAME_CH = "trade_name_ch";	//交易名称(中文)
	String TRADE_NAME_EN = "trade_name_en";	//交易名称(英文)
	String TRADE_CODE_FR = "trade_code_fr";	//柜面交易码
	String TRADE_TYPE = "trade_type";	//所属业务类别
	/** 设备参数变量名 **/
	String DEVICETYPE = "devicetype";	//设备类型
	String DEVICE_MODEL = "device_model";	//设备设备型号
	String DEVICE_IP = "device_ip";	//设备IP
	String DEVICE_OID = "device_oid";	//设备oid
	String DEVICESIZE = "devicesize";//设备信息条数
	String DEVICE = "device";//设备信息
	String DEVICE_PARAM_ID = "device_param_id";	//设备参数ID
	String DEVICE_NUM = "device_num"; //设备编号
	String DEVICE_NAME = "device_name";	//设备名称
	String DEVICE_STATUS = "device_status";//设备状态
	String DEVICE_CHK_TYPE = "device_chk_type";//设备校验类型
	String DEVICE_CHK_INFO = "device_chk_info";//设备校验信息
	/** 手持参数变量名 **/
	String MOBDEVICE_NUM = "mobdevice_num"; //手持设备编号
	String MOBTYPE = "mobtype";	//手持类型
	String DEVICEMODEL = "devicemodel";	//手持设备型号
	String MOB_IP = "mob_ip";	//设备IP
	String MOB_OID = "mob_oid";	//手持设备oid
	String CITY = "city";	//所在城市
	String MOBSIZE = "mobsize";	//手持设备信息条数
	String MOB = "mob";	//手持设备信息
	String MOBDEVICE_NAME = "mobdevice_name";	//手持设备名称
	String MOB_PARAM_ID = "mob_param_id";	//手持设备参数ID
	String DEVICEADM_NUM = "deviceadm_num";	//设备管理柜员号
	String DEVICEADM_NAME = "deviceadm_name";	//设备管理柜员名称
	String PARAMSIZE = "paramsize";	//手持设备参数条数
	String PARAM = "param";	//手持设备参数
	String TIMEING_TIMEOUT = "timeing_timeout";	//页面超时返回时间
	String MOB_PWD = "mob_pwd";	//手持设备设置密码
	String LABEL_CONTENT = "label_content";	//标签内容
	String LABEL_TEXT_FONT = "label_text_font";	//标签内容
	String LABEL_TEXT_STYLE = "label_text_style";	//标签内容
	String LABEL_TEXT_SIZE = "label_text_size";	//标签内容
	String LABEL_TEXT_COLOR = "label_text_color";	//标签内容
	String INTERFACESIZE = "interfacesize";	//手持设备界面条数
	String INTERFACE = "interface";	//手持设备界面
	String NODE_INDEX = "node_index";	//节点索引

	/**查询预填单变量名**/
	String PER_NAME = "per_name";//办理人姓名
	String PER_TYPE = "per_type";//办理人证件类型
	String PER_NUM = "per_num";//办理人证件号
	String PER_PHONE = "per_phone";//办理人手机号
	String AGENT_NAME = "agent_name";//代理人姓名
	String AGENT_NANE = "agent_nane";//代理人姓名
	String AGENT_TYPE = "agent_type";//代理人证件类型
	String AGENT_NUM = "agent_num";//代理人证件号
	String AGENT_PHONE = "agent_phone";//代理人手机号
	String MEDIUM_TYPE = "medium_type";//介质类型
	String MEDIUM_NUM = "medium_num";//介质号码
	String PFS_CONTENT = "pfs_content";//填单内容
	String PFS_SEQ = "pfs_seq";//预填单流水号
	String CONTINUE_FLAG = "continue_flag";//继续填单标志
	
	/**休眠排队号查询**/
	String NOTI_VAITNUM = "noti_waitnum";//客户设定的通知等待人数阈值
	String WINDOW_IP = "window_ip";//处理窗口IP
	String VCALLTIME = "vcalltime";//虚拟叫号时间
	String VCALLTIME_2 = "vcalltime_2";//按等待时间计算的虚拟时间
	String CHECK_QUEUE_VALUE = "check_queue_value";//排队号验证码
	
	/**客户信息处理**/
	String OCUSTTYPE_E = "ocusttype_e";//原客户类型
	String NCUSTTYPE_E = "ncusttype_e";//新客户类型
	String SERVETIME = "servetime";//服务期限
	
	/**自助设备参数**/
	String PARAMETER_ID="parameter_id";//自助设备参数ID
	String TIME_SHUTDOWN ="time_shutdown";//关机时间
	String TIME_PAGEOUT="time_pageout";//界面超时时间
	String TIME_ADDSHOW="time_addshow";//广告播放时间
	String TIME_MAINSHOW="time_mainshow";//主界面显示时间
	/**费率信息**/
	String CUST_NOTE = "cust_note";
	String PRODUCE_NAME = "produce_name";//产品名称
	String CHARGE_PROJECT ="charge_project";//收费项目
	String CHARGE_CRITERION = "charge_criterion";//收费标准
	String CHARGE_REASON = "charge_reason";//收费依据
	String UPDATE_TIME = "update_time";//修改日期
}