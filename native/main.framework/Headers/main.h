#import <Foundation/Foundation.h>

@class MainFeedImpl, MainSqldelight_runtimeQuery, MainKotlinUnit, MainSqldelight_runtimeTransacterTransaction, MainItemImpl, MainMaxIdImpl, MainMaxIdAllImpl, MainMinIdImpl, MainSqlDelightDatabaseCompanion, MainUserImpl, MainApi, MainNextcloudNewsVersion, MainDatabase, MainExploreFeed, MainExploreFeedCompanion, MainExploreFeed$serializer, MainKotlinArray, MainNextcloudNewsFeed, MainNextcloudNewsFeedCompanion, MainNextcloudNewsFeed$serializer, MainNextcloudNewsFolder, MainNextcloudNewsFolderCompanion, MainNextcloudNewsFolder$serializer, MainNextcloudNewsItem, MainNextcloudNewsItemCompanion, MainNextcloudNewsItem$serializer, MainNextcloudNewsVersionCompanion, MainNextcloudNewsVersion$serializer, MainKotlinx_serialization_runtime_nativeEnumDescriptor, MainKotlinx_serialization_runtime_nativeSerialKind, MainKotlinNothing, MainKotlinx_serialization_runtime_nativeUpdateMode, MainKotlinByteArray, MainKotlinx_serialization_runtime_nativeSerialClassDescImpl, MainKotlinEnum, MainKotlinByteIterator;

@protocol MainFeed, MainFeedQueries, MainSqldelight_runtimeTransacter, MainItem, MainItemQueries, MainMaxId, MainMaxIdAll, MainMinId, MainSqlDelightDatabase, MainUserQueries, MainSqldelight_runtimeSqlDriver, MainSqldelight_runtimeSqlDriverSchema, MainUser, MainKotlinx_serialization_runtime_nativeKSerializer, MainKotlinx_serialization_runtime_nativeGeneratedSerializer, MainKotlinx_serialization_runtime_nativeSerializationStrategy, MainKotlinx_serialization_runtime_nativeEncoder, MainKotlinx_serialization_runtime_nativeSerialDescriptor, MainKotlinx_serialization_runtime_nativeDeserializationStrategy, MainKotlinx_serialization_runtime_nativeDecoder, MainKotlinSuspendFunction1, MainSqldelight_runtimeSqlCursor, MainSqldelight_runtimeQueryListener, MainSqldelight_runtimeSqlPreparedStatement, MainSqldelight_runtimeCloseable, MainKotlinIterator, MainKotlinx_serialization_runtime_nativeCompositeEncoder, MainKotlinx_serialization_runtime_nativeSerialModule, MainKotlinAnnotation, MainKotlinx_serialization_runtime_nativeCompositeDecoder, MainKotlinSuspendFunction, MainKotlinx_serialization_runtime_nativeSerialModuleCollector, MainKotlinKClass, MainKotlinComparable, MainKotlinKDeclarationContainer, MainKotlinKAnnotatedElement, MainKotlinKClassifier;

NS_ASSUME_NONNULL_BEGIN

@interface KotlinBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface KotlinBase (KotlinBaseCopying) <NSCopying>
@end;

__attribute__((objc_runtime_name("KotlinMutableSet")))
__attribute__((swift_name("KotlinMutableSet")))
@interface MainMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((objc_runtime_name("KotlinMutableDictionary")))
__attribute__((swift_name("KotlinMutableDictionary")))
@interface MainMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((objc_runtime_name("KotlinNumber")))
__attribute__((swift_name("KotlinNumber")))
@interface MainNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end;

__attribute__((objc_runtime_name("KotlinByte")))
__attribute__((swift_name("KotlinByte")))
@interface MainByte : MainNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((objc_runtime_name("KotlinUByte")))
__attribute__((swift_name("KotlinUByte")))
@interface MainUByte : MainNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((objc_runtime_name("KotlinShort")))
__attribute__((swift_name("KotlinShort")))
@interface MainShort : MainNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((objc_runtime_name("KotlinUShort")))
__attribute__((swift_name("KotlinUShort")))
@interface MainUShort : MainNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((objc_runtime_name("KotlinInt")))
__attribute__((swift_name("KotlinInt")))
@interface MainInt : MainNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((objc_runtime_name("KotlinUInt")))
__attribute__((swift_name("KotlinUInt")))
@interface MainUInt : MainNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((objc_runtime_name("KotlinLong")))
__attribute__((swift_name("KotlinLong")))
@interface MainLong : MainNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((objc_runtime_name("KotlinULong")))
__attribute__((swift_name("KotlinULong")))
@interface MainULong : MainNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((objc_runtime_name("KotlinFloat")))
__attribute__((swift_name("KotlinFloat")))
@interface MainFloat : MainNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((objc_runtime_name("KotlinDouble")))
__attribute__((swift_name("KotlinDouble")))
@interface MainDouble : MainNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((objc_runtime_name("KotlinBoolean")))
__attribute__((swift_name("KotlinBoolean")))
@interface MainBoolean : MainNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((swift_name("Feed")))
@protocol MainFeed
@required
@property (readonly) int64_t id __attribute__((swift_name("id")));
@property (readonly) NSString *title __attribute__((swift_name("title")));
@property (readonly) NSString *faviconUrl __attribute__((swift_name("faviconUrl")));
@property (readonly) int64_t unreadCount __attribute__((swift_name("unreadCount")));
@property (readonly) int64_t ordering __attribute__((swift_name("ordering")));
@property (readonly) int64_t isFolder __attribute__((swift_name("isFolder")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("FeedImpl")))
@interface MainFeedImpl : KotlinBase <MainFeed>
- (instancetype)initWithId:(int64_t)id title:(NSString *)title faviconUrl:(NSString *)faviconUrl unreadCount:(int64_t)unreadCount ordering:(int64_t)ordering isFolder:(int64_t)isFolder __attribute__((swift_name("init(id:title:faviconUrl:unreadCount:ordering:isFolder:)"))) __attribute__((objc_designated_initializer));
- (int64_t)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (int64_t)component4 __attribute__((swift_name("component4()")));
- (int64_t)component5 __attribute__((swift_name("component5()")));
- (int64_t)component6 __attribute__((swift_name("component6()")));
- (MainFeedImpl *)doCopyId:(int64_t)id title:(NSString *)title faviconUrl:(NSString *)faviconUrl unreadCount:(int64_t)unreadCount ordering:(int64_t)ordering isFolder:(int64_t)isFolder __attribute__((swift_name("doCopy(id:title:faviconUrl:unreadCount:ordering:isFolder:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeTransacter")))
@protocol MainSqldelight_runtimeTransacter
@required
- (void)transactionNoEnclosing:(BOOL)noEnclosing body:(MainKotlinUnit *(^)(MainSqldelight_runtimeTransacterTransaction *))body __attribute__((swift_name("transaction(noEnclosing:body:)")));
@end;

__attribute__((swift_name("FeedQueries")))
@protocol MainFeedQueries <MainSqldelight_runtimeTransacter>
@required
- (MainSqldelight_runtimeQuery *)selectAllByUnreadCountMapper:(id (^)(MainLong *, NSString *, NSString *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectAllByUnreadCount(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectAllByUnreadCount __attribute__((swift_name("selectAllByUnreadCount()")));
- (MainSqldelight_runtimeQuery *)selectAllByUnreadCountAndFolderMapper:(id (^)(MainLong *, NSString *, NSString *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectAllByUnreadCountAndFolder(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectAllByUnreadCountAndFolder __attribute__((swift_name("selectAllByUnreadCountAndFolder()")));
- (MainSqldelight_runtimeQuery *)selectAllByTitleMapper:(id (^)(MainLong *, NSString *, NSString *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectAllByTitle(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectAllByTitle __attribute__((swift_name("selectAllByTitle()")));
- (MainSqldelight_runtimeQuery *)selectAllByTitleAndFolderMapper:(id (^)(MainLong *, NSString *, NSString *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectAllByTitleAndFolder(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectAllByTitleAndFolder __attribute__((swift_name("selectAllByTitleAndFolder()")));
- (MainSqldelight_runtimeQuery *)selectFolderFeedByIdId:(int64_t)id mapper:(id (^)(MainLong *, NSString *, NSString *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectFolderFeedById(id:mapper:)")));
- (MainSqldelight_runtimeQuery *)selectFolderFeedByIdId:(int64_t)id __attribute__((swift_name("selectFolderFeedById(id:)")));
- (MainSqldelight_runtimeQuery *)countUnread __attribute__((swift_name("countUnread()")));
- (void)renameFolderFeedTitle:(NSString *)title id:(int64_t)id __attribute__((swift_name("renameFolderFeed(title:id:)")));
- (void)renameFeedTitle:(NSString *)title id:(int64_t)id __attribute__((swift_name("renameFeed(title:id:)")));
- (void)insertId:(int64_t)id title:(NSString *)title faviconUrl:(NSString *)faviconUrl unreadCount:(int64_t)unreadCount ordering:(int64_t)ordering isFolder:(int64_t)isFolder __attribute__((swift_name("insert(id:title:faviconUrl:unreadCount:ordering:isFolder:)")));
- (void)increaseUnreadCountForFolderFeedUnreadCount:(int64_t)unreadCount id:(int64_t)id __attribute__((swift_name("increaseUnreadCountForFolderFeed(unreadCount:id:)")));
- (void)markAllAsRead __attribute__((swift_name("markAllAsRead()")));
- (void)markAsReadId:(int64_t)id isFolder:(int64_t)isFolder __attribute__((swift_name("markAsRead(id:isFolder:)")));
- (void)decreaseUnreadCountId:(int64_t)id isFolder:(int64_t)isFolder __attribute__((swift_name("decreaseUnreadCount(id:isFolder:)")));
- (void)deleteFolderFeedId:(int64_t)id __attribute__((swift_name("deleteFolderFeed(id:)")));
- (void)deleteFeedId:(int64_t)id __attribute__((swift_name("deleteFeed(id:)")));
- (void)clear __attribute__((swift_name("clear()")));
@end;

__attribute__((swift_name("Item")))
@protocol MainItem
@required
@property (readonly) int64_t id __attribute__((swift_name("id")));
@property (readonly) NSString *guidHash __attribute__((swift_name("guidHash")));
@property (readonly) int64_t feedId __attribute__((swift_name("feedId")));
@property (readonly) NSString *title __attribute__((swift_name("title")));
@property (readonly) NSString *imageUrl __attribute__((swift_name("imageUrl")));
@property (readonly) NSString *url __attribute__((swift_name("url")));
@property (readonly) int64_t isUnread __attribute__((swift_name("isUnread")));
@property (readonly) int64_t isFolder __attribute__((swift_name("isFolder")));
@property (readonly) int64_t isStarred __attribute__((swift_name("isStarred")));
@property (readonly) int64_t creationDate __attribute__((swift_name("creationDate")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ItemImpl")))
@interface MainItemImpl : KotlinBase <MainItem>
- (instancetype)initWithId:(int64_t)id guidHash:(NSString *)guidHash feedId:(int64_t)feedId title:(NSString *)title imageUrl:(NSString *)imageUrl url:(NSString *)url isUnread:(int64_t)isUnread isFolder:(int64_t)isFolder isStarred:(int64_t)isStarred creationDate:(int64_t)creationDate __attribute__((swift_name("init(id:guidHash:feedId:title:imageUrl:url:isUnread:isFolder:isStarred:creationDate:)"))) __attribute__((objc_designated_initializer));
- (int64_t)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (int64_t)component3 __attribute__((swift_name("component3()")));
- (NSString *)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (NSString *)component6 __attribute__((swift_name("component6()")));
- (int64_t)component7 __attribute__((swift_name("component7()")));
- (int64_t)component8 __attribute__((swift_name("component8()")));
- (int64_t)component9 __attribute__((swift_name("component9()")));
- (int64_t)component10 __attribute__((swift_name("component10()")));
- (MainItemImpl *)doCopyId:(int64_t)id guidHash:(NSString *)guidHash feedId:(int64_t)feedId title:(NSString *)title imageUrl:(NSString *)imageUrl url:(NSString *)url isUnread:(int64_t)isUnread isFolder:(int64_t)isFolder isStarred:(int64_t)isStarred creationDate:(int64_t)creationDate __attribute__((swift_name("doCopy(id:guidHash:feedId:title:imageUrl:url:isUnread:isFolder:isStarred:creationDate:)")));
@end;

__attribute__((swift_name("ItemQueries")))
@protocol MainItemQueries <MainSqldelight_runtimeTransacter>
@required
- (MainSqldelight_runtimeQuery *)selectAllByFeedIdAndTypeFeedId:(int64_t)feedId isFolder:(int64_t)isFolder mapper:(id (^)(MainLong *, NSString *, MainLong *, NSString *, NSString *, NSString *, MainLong *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectAllByFeedIdAndType(feedId:isFolder:mapper:)")));
- (MainSqldelight_runtimeQuery *)selectAllByFeedIdAndTypeFeedId:(int64_t)feedId isFolder:(int64_t)isFolder __attribute__((swift_name("selectAllByFeedIdAndType(feedId:isFolder:)")));
- (MainSqldelight_runtimeQuery *)selectUnreadMapper:(id (^)(MainLong *, NSString *, MainLong *, NSString *, NSString *, NSString *, MainLong *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectUnread(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectUnread __attribute__((swift_name("selectUnread()")));
- (MainSqldelight_runtimeQuery *)selectStarredMapper:(id (^)(MainLong *, NSString *, MainLong *, NSString *, NSString *, NSString *, MainLong *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectStarred(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectStarred __attribute__((swift_name("selectStarred()")));
- (MainSqldelight_runtimeQuery *)countStarred __attribute__((swift_name("countStarred()")));
- (MainSqldelight_runtimeQuery *)countUnstarred __attribute__((swift_name("countUnstarred()")));
- (MainSqldelight_runtimeQuery *)maxIdAllMapper:(id (^)(MainLong * _Nullable))mapper __attribute__((swift_name("maxIdAll(mapper:)")));
- (MainSqldelight_runtimeQuery *)maxIdAll __attribute__((swift_name("maxIdAll()")));
- (MainSqldelight_runtimeQuery *)maxIdFeedId:(int64_t)feedId isFolder:(int64_t)isFolder mapper:(id (^)(MainLong * _Nullable))mapper __attribute__((swift_name("maxId(feedId:isFolder:mapper:)")));
- (MainSqldelight_runtimeQuery *)maxIdFeedId:(int64_t)feedId isFolder:(int64_t)isFolder __attribute__((swift_name("maxId(feedId:isFolder:)")));
- (MainSqldelight_runtimeQuery *)minIdFeedId:(int64_t)feedId isFolder:(int64_t)isFolder mapper:(id (^)(MainLong * _Nullable))mapper __attribute__((swift_name("minId(feedId:isFolder:mapper:)")));
- (MainSqldelight_runtimeQuery *)minIdFeedId:(int64_t)feedId isFolder:(int64_t)isFolder __attribute__((swift_name("minId(feedId:isFolder:)")));
- (void)insertId:(MainLong * _Nullable)id guidHash:(NSString *)guidHash feedId:(int64_t)feedId title:(NSString *)title imageUrl:(NSString *)imageUrl url:(NSString *)url isUnread:(int64_t)isUnread isFolder:(int64_t)isFolder isStarred:(int64_t)isStarred __attribute__((swift_name("insert(id:guidHash:feedId:title:imageUrl:url:isUnread:isFolder:isStarred:)")));
- (void)markAllAsRead __attribute__((swift_name("markAllAsRead()")));
- (void)markItemAsReadId:(int64_t)id __attribute__((swift_name("markItemAsRead(id:)")));
- (void)markItemAsStarredId:(int64_t)id __attribute__((swift_name("markItemAsStarred(id:)")));
- (void)markItemAsUnstarredId:(int64_t)id __attribute__((swift_name("markItemAsUnstarred(id:)")));
- (void)markAsReadFeedId:(int64_t)feedId isFolder:(int64_t)isFolder __attribute__((swift_name("markAsRead(feedId:isFolder:)")));
- (void)clear __attribute__((swift_name("clear()")));
- (void)clearOld __attribute__((swift_name("clearOld()")));
@end;

__attribute__((swift_name("MaxId")))
@protocol MainMaxId
@required
@property (readonly) MainLong * _Nullable MAX __attribute__((swift_name("MAX")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MaxIdImpl")))
@interface MainMaxIdImpl : KotlinBase <MainMaxId>
- (instancetype)initWithMAX:(MainLong * _Nullable)MAX __attribute__((swift_name("init(MAX:)"))) __attribute__((objc_designated_initializer));
- (MainLong * _Nullable)component1 __attribute__((swift_name("component1()")));
- (MainMaxIdImpl *)doCopyMAX:(MainLong * _Nullable)MAX __attribute__((swift_name("doCopy(MAX:)")));
@end;

__attribute__((swift_name("MaxIdAll")))
@protocol MainMaxIdAll
@required
@property (readonly) MainLong * _Nullable MAX __attribute__((swift_name("MAX")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MaxIdAllImpl")))
@interface MainMaxIdAllImpl : KotlinBase <MainMaxIdAll>
- (instancetype)initWithMAX:(MainLong * _Nullable)MAX __attribute__((swift_name("init(MAX:)"))) __attribute__((objc_designated_initializer));
- (MainLong * _Nullable)component1 __attribute__((swift_name("component1()")));
- (MainMaxIdAllImpl *)doCopyMAX:(MainLong * _Nullable)MAX __attribute__((swift_name("doCopy(MAX:)")));
@end;

__attribute__((swift_name("MinId")))
@protocol MainMinId
@required
@property (readonly) MainLong * _Nullable MIN __attribute__((swift_name("MIN")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("MinIdImpl")))
@interface MainMinIdImpl : KotlinBase <MainMinId>
- (instancetype)initWithMIN:(MainLong * _Nullable)MIN __attribute__((swift_name("init(MIN:)"))) __attribute__((objc_designated_initializer));
- (MainLong * _Nullable)component1 __attribute__((swift_name("component1()")));
- (MainMinIdImpl *)doCopyMIN:(MainLong * _Nullable)MIN __attribute__((swift_name("doCopy(MIN:)")));
@end;

__attribute__((swift_name("SqlDelightDatabase")))
@protocol MainSqlDelightDatabase <MainSqldelight_runtimeTransacter>
@required
@property (readonly) id<MainFeedQueries> feedQueries __attribute__((swift_name("feedQueries")));
@property (readonly) id<MainItemQueries> itemQueries __attribute__((swift_name("itemQueries")));
@property (readonly) id<MainUserQueries> userQueries __attribute__((swift_name("userQueries")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SqlDelightDatabaseCompanion")))
@interface MainSqlDelightDatabaseCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<MainSqlDelightDatabase>)invokeDriver:(id<MainSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("invoke(driver:)")));
@property (readonly) id<MainSqldelight_runtimeSqlDriverSchema> Schema __attribute__((swift_name("Schema")));
@end;

__attribute__((swift_name("User")))
@protocol MainUser
@required
@property (readonly) int64_t sorting __attribute__((swift_name("sorting")));
@property (readonly) int64_t isFolderTop __attribute__((swift_name("isFolderTop")));
@property (readonly) int64_t lastFeedFetch __attribute__((swift_name("lastFeedFetch")));
@property (readonly) int64_t lastStarredFetch __attribute__((swift_name("lastStarredFetch")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UserImpl")))
@interface MainUserImpl : KotlinBase <MainUser>
- (instancetype)initWithSorting:(int64_t)sorting isFolderTop:(int64_t)isFolderTop lastFeedFetch:(int64_t)lastFeedFetch lastStarredFetch:(int64_t)lastStarredFetch __attribute__((swift_name("init(sorting:isFolderTop:lastFeedFetch:lastStarredFetch:)"))) __attribute__((objc_designated_initializer));
- (int64_t)component1 __attribute__((swift_name("component1()")));
- (int64_t)component2 __attribute__((swift_name("component2()")));
- (int64_t)component3 __attribute__((swift_name("component3()")));
- (int64_t)component4 __attribute__((swift_name("component4()")));
- (MainUserImpl *)doCopySorting:(int64_t)sorting isFolderTop:(int64_t)isFolderTop lastFeedFetch:(int64_t)lastFeedFetch lastStarredFetch:(int64_t)lastStarredFetch __attribute__((swift_name("doCopy(sorting:isFolderTop:lastFeedFetch:lastStarredFetch:)")));
@end;

__attribute__((swift_name("UserQueries")))
@protocol MainUserQueries <MainSqldelight_runtimeTransacter>
@required
- (MainSqldelight_runtimeQuery *)selectAllMapper:(id (^)(MainLong *, MainLong *, MainLong *, MainLong *))mapper __attribute__((swift_name("selectAll(mapper:)")));
- (MainSqldelight_runtimeQuery *)selectAll __attribute__((swift_name("selectAll()")));
- (void)insert __attribute__((swift_name("insert()")));
- (void)updateSortingSorting:(int64_t)sorting __attribute__((swift_name("updateSorting(sorting:)")));
- (void)updateFolderTopIsFolderTop:(int64_t)isFolderTop __attribute__((swift_name("updateFolderTop(isFolderTop:)")));
- (void)updateFeedCacheLastFeedFetch:(int64_t)lastFeedFetch __attribute__((swift_name("updateFeedCache(lastFeedFetch:)")));
- (void)updateStarredCacheLastStarredFetch:(int64_t)lastStarredFetch __attribute__((swift_name("updateStarredCache(lastStarredFetch:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Api")))
@interface MainApi : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)api __attribute__((swift_name("init()")));
- (void)setCredentialsUrl:(NSString *)url email:(NSString *)email password:(NSString *)password __attribute__((swift_name("setCredentials(url:email:password:)")));
- (BOOL)isCredentialsAvailable __attribute__((swift_name("isCredentialsAvailable()")));
- (void)loginUrl:(NSString *)url email:(NSString *)email password:(NSString *)password callback:(MainKotlinUnit *(^)(MainNextcloudNewsVersion *))callback unauthorized:(MainKotlinUnit *(^)(void))unauthorized error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("login(url:email:password:callback:unauthorized:error:)")));
- (void)createAccountUrl:(NSString *)url email:(NSString *)email password:(NSString *)password success:(MainKotlinUnit *(^)(void))success userExists:(MainKotlinUnit *(^)(void))userExists error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("createAccount(url:email:password:success:userExists:error:)")));
- (void)markFeedAsReadFeedId:(int64_t)feedId callback:(MainKotlinUnit *(^)(NSArray<id<MainItem>> *))callback __attribute__((swift_name("markFeedAsRead(feedId:callback:)")));
- (void)markFolderAsReadFolderId:(int64_t)folderId callback:(MainKotlinUnit *(^)(NSArray<id<MainItem>> *))callback __attribute__((swift_name("markFolderAsRead(folderId:callback:)")));
- (void)markAllAsReadCallback:(MainKotlinUnit *(^)(NSArray<id<MainFeed>> *))callback __attribute__((swift_name("markAllAsRead(callback:)")));
- (void)markItemAsReadItemId:(int64_t)itemId __attribute__((swift_name("markItemAsRead(itemId:)")));
- (void)markItemAsStarredFeedId:(int64_t)feedId guidHash:(NSString *)guidHash __attribute__((swift_name("markItemAsStarred(feedId:guidHash:)")));
- (void)markItemAsUnstarredFeedId:(int64_t)feedId guidHash:(NSString *)guidHash __attribute__((swift_name("markItemAsUnstarred(feedId:guidHash:)")));
- (void)getFeedsCallback:(MainKotlinUnit *(^)(NSArray<id<MainFeed>> *))callback error:(MainKotlinUnit *(^)(void))error unauthorized:(MainKotlinUnit *(^)(void))unauthorized __attribute__((swift_name("getFeeds(callback:error:unauthorized:)")));
- (void)renameFolderId:(int64_t)id title:(NSString *)title callback:(MainKotlinUnit *(^)(void))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("renameFolder(id:title:callback:error:)")));
- (void)renameFeedId:(int64_t)id title:(NSString *)title callback:(MainKotlinUnit *(^)(void))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("renameFeed(id:title:callback:error:)")));
- (void)deleteFeedId:(int64_t)id callback:(MainKotlinUnit *(^)(void))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("deleteFeed(id:callback:error:)")));
- (void)deleteFolderId:(int64_t)id callback:(MainKotlinUnit *(^)(void))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("deleteFolder(id:callback:error:)")));
- (void)createFeedUrl:(NSString *)url folderId:(int64_t)folderId callback:(MainKotlinUnit *(^)(void))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("createFeed(url:folderId:callback:error:)")));
- (void)getStarredItemsCallback:(MainKotlinUnit *(^)(NSArray<id<MainItem>> *))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("getStarredItems(callback:error:)")));
- (void)getUnreadItemsCallback:(MainKotlinUnit *(^)(NSArray<id<MainItem>> *))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("getUnreadItems(callback:error:)")));
- (void)getItemsId:(int64_t)id type:(int64_t)type offset:(BOOL)offset callback:(MainKotlinUnit *(^)(NSArray<id<MainItem>> *))callback error:(MainKotlinUnit *(^)(void))error __attribute__((swift_name("getItems(id:type:offset:callback:error:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Database")))
@interface MainDatabase : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)database __attribute__((swift_name("init()")));
- (void)setup __attribute__((swift_name("setup()")));
- (id<MainFeedQueries> _Nullable)getFeedQueries __attribute__((swift_name("getFeedQueries()")));
- (id<MainItemQueries> _Nullable)getItemQueries __attribute__((swift_name("getItemQueries()")));
- (id<MainUserQueries> _Nullable)getUserQueries __attribute__((swift_name("getUserQueries()")));
- (NSMutableArray<id<MainFeed>> *)getFeeds __attribute__((swift_name("getFeeds()")));
- (NSMutableArray<id<MainItem>> *)getItemsFeedId:(int64_t)feedId type:(int64_t)type __attribute__((swift_name("getItems(feedId:type:)")));
- (id<MainUser> _Nullable)getUser __attribute__((swift_name("getUser()")));
- (int64_t)getTotalUnreadCount __attribute__((swift_name("getTotalUnreadCount()")));
- (int64_t)getTotalStarredCount __attribute__((swift_name("getTotalStarredCount()")));
- (void)clear __attribute__((swift_name("clear()")));
@property (readonly) int64_t SORT_UNREADCOUNT __attribute__((swift_name("SORT_UNREADCOUNT")));
@property (readonly) int64_t SORT_TITLE __attribute__((swift_name("SORT_TITLE")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExploreFeed")))
@interface MainExploreFeed : KotlinBase
- (instancetype)initWithTitle:(NSString *)title url:(NSString *)url __attribute__((swift_name("init(title:url:)"))) __attribute__((objc_designated_initializer));
- (NSString *)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (MainExploreFeed *)doCopyTitle:(NSString *)title url:(NSString *)url __attribute__((swift_name("doCopy(title:url:)")));
@property NSString *title __attribute__((swift_name("title")));
@property NSString *url __attribute__((swift_name("url")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExploreFeed.Companion")))
@interface MainExploreFeedCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer>)serializer __attribute__((swift_name("serializer()")));
@property (readonly) NSArray<MainExploreFeed *> *exploreFeeds __attribute__((swift_name("exploreFeeds")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeSerializationStrategy")))
@protocol MainKotlinx_serialization_runtime_nativeSerializationStrategy
@required
- (void)serializeEncoder:(id<MainKotlinx_serialization_runtime_nativeEncoder>)encoder obj:(id _Nullable)obj __attribute__((swift_name("serialize(encoder:obj:)")));
@property (readonly) id<MainKotlinx_serialization_runtime_nativeSerialDescriptor> descriptor __attribute__((swift_name("descriptor")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeDeserializationStrategy")))
@protocol MainKotlinx_serialization_runtime_nativeDeserializationStrategy
@required
- (id _Nullable)deserializeDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
- (id _Nullable)patchDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder old:(id _Nullable)old __attribute__((swift_name("patch(decoder:old:)")));
@property (readonly) id<MainKotlinx_serialization_runtime_nativeSerialDescriptor> descriptor __attribute__((swift_name("descriptor")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeKSerializer")))
@protocol MainKotlinx_serialization_runtime_nativeKSerializer <MainKotlinx_serialization_runtime_nativeSerializationStrategy, MainKotlinx_serialization_runtime_nativeDeserializationStrategy>
@required
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeGeneratedSerializer")))
@protocol MainKotlinx_serialization_runtime_nativeGeneratedSerializer <MainKotlinx_serialization_runtime_nativeKSerializer>
@required
- (MainKotlinArray *)childSerializers __attribute__((swift_name("childSerializers()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExploreFeed.$serializer")))
@interface MainExploreFeed$serializer : KotlinBase <MainKotlinx_serialization_runtime_nativeGeneratedSerializer>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)$serializer __attribute__((swift_name("init()")));
- (MainExploreFeed *)deserializeDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
- (MainExploreFeed *)patchDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder old:(MainExploreFeed *)old __attribute__((swift_name("patch(decoder:old:)")));
- (void)serializeEncoder:(id<MainKotlinx_serialization_runtime_nativeEncoder>)encoder obj:(MainExploreFeed *)obj __attribute__((swift_name("serialize(encoder:obj:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsFeed")))
@interface MainNextcloudNewsFeed : KotlinBase
- (instancetype)initWithId:(int64_t)id url:(NSString *)url title:(NSString *)title folderId:(int64_t)folderId faviconLink:(NSString *)faviconLink unreadCount:(int64_t)unreadCount ordering:(int64_t)ordering __attribute__((swift_name("init(id:url:title:folderId:faviconLink:unreadCount:ordering:)"))) __attribute__((objc_designated_initializer));
- (int64_t)component1 __attribute__((swift_name("component1()")));
- (NSString *)component2 __attribute__((swift_name("component2()")));
- (NSString *)component3 __attribute__((swift_name("component3()")));
- (int64_t)component4 __attribute__((swift_name("component4()")));
- (NSString *)component5 __attribute__((swift_name("component5()")));
- (int64_t)component6 __attribute__((swift_name("component6()")));
- (int64_t)component7 __attribute__((swift_name("component7()")));
- (MainNextcloudNewsFeed *)doCopyId:(int64_t)id url:(NSString *)url title:(NSString *)title folderId:(int64_t)folderId faviconLink:(NSString *)faviconLink unreadCount:(int64_t)unreadCount ordering:(int64_t)ordering __attribute__((swift_name("doCopy(id:url:title:folderId:faviconLink:unreadCount:ordering:)")));
@property (readonly) int64_t id __attribute__((swift_name("id")));
@property (readonly) NSString *url __attribute__((swift_name("url")));
@property (readonly) NSString *title __attribute__((swift_name("title")));
@property (readonly) int64_t folderId __attribute__((swift_name("folderId")));
@property (readonly) NSString *faviconLink __attribute__((swift_name("faviconLink")));
@property (readonly) int64_t unreadCount __attribute__((swift_name("unreadCount")));
@property (readonly) int64_t ordering __attribute__((swift_name("ordering")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsFeed.Companion")))
@interface MainNextcloudNewsFeedCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsFeed.$serializer")))
@interface MainNextcloudNewsFeed$serializer : KotlinBase <MainKotlinx_serialization_runtime_nativeGeneratedSerializer>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)$serializer __attribute__((swift_name("init()")));
- (MainNextcloudNewsFeed *)deserializeDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
- (MainNextcloudNewsFeed *)patchDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder old:(MainNextcloudNewsFeed *)old __attribute__((swift_name("patch(decoder:old:)")));
- (void)serializeEncoder:(id<MainKotlinx_serialization_runtime_nativeEncoder>)encoder obj:(MainNextcloudNewsFeed *)obj __attribute__((swift_name("serialize(encoder:obj:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsFolder")))
@interface MainNextcloudNewsFolder : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (readonly) int64_t id __attribute__((swift_name("id")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsFolder.Companion")))
@interface MainNextcloudNewsFolderCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsFolder.$serializer")))
@interface MainNextcloudNewsFolder$serializer : KotlinBase <MainKotlinx_serialization_runtime_nativeGeneratedSerializer>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)$serializer __attribute__((swift_name("init()")));
- (MainNextcloudNewsFolder *)deserializeDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
- (MainNextcloudNewsFolder *)patchDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder old:(MainNextcloudNewsFolder *)old __attribute__((swift_name("patch(decoder:old:)")));
- (void)serializeEncoder:(id<MainKotlinx_serialization_runtime_nativeEncoder>)encoder obj:(MainNextcloudNewsFolder *)obj __attribute__((swift_name("serialize(encoder:obj:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsItem")))
@interface MainNextcloudNewsItem : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (readonly) int64_t id __attribute__((swift_name("id")));
@property (readonly) NSString *guidHash __attribute__((swift_name("guidHash")));
@property (readonly) NSString *url __attribute__((swift_name("url")));
@property (readonly) NSString *title __attribute__((swift_name("title")));
@property (readonly) NSString *body __attribute__((swift_name("body")));
@property BOOL unread __attribute__((swift_name("unread")));
@property BOOL starred __attribute__((swift_name("starred")));
@property int64_t feedId __attribute__((swift_name("feedId")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsItem.Companion")))
@interface MainNextcloudNewsItemCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsItem.$serializer")))
@interface MainNextcloudNewsItem$serializer : KotlinBase <MainKotlinx_serialization_runtime_nativeGeneratedSerializer>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)$serializer __attribute__((swift_name("init()")));
- (MainNextcloudNewsItem *)deserializeDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
- (MainNextcloudNewsItem *)patchDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder old:(MainNextcloudNewsItem *)old __attribute__((swift_name("patch(decoder:old:)")));
- (void)serializeEncoder:(id<MainKotlinx_serialization_runtime_nativeEncoder>)encoder obj:(MainNextcloudNewsItem *)obj __attribute__((swift_name("serialize(encoder:obj:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsVersion")))
@interface MainNextcloudNewsVersion : KotlinBase
- (instancetype)initWithVersion:(NSString * _Nullable)version __attribute__((swift_name("init(version:)"))) __attribute__((objc_designated_initializer));
- (NSString * _Nullable)component1 __attribute__((swift_name("component1()")));
- (MainNextcloudNewsVersion *)doCopyVersion:(NSString * _Nullable)version __attribute__((swift_name("doCopy(version:)")));
@property NSString * _Nullable version __attribute__((swift_name("version")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsVersion.Companion")))
@interface MainNextcloudNewsVersionCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer>)serializer __attribute__((swift_name("serializer()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NextcloudNewsVersion.$serializer")))
@interface MainNextcloudNewsVersion$serializer : KotlinBase <MainKotlinx_serialization_runtime_nativeGeneratedSerializer>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)$serializer __attribute__((swift_name("init()")));
- (MainNextcloudNewsVersion *)deserializeDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder __attribute__((swift_name("deserialize(decoder:)")));
- (MainNextcloudNewsVersion *)patchDecoder:(id<MainKotlinx_serialization_runtime_nativeDecoder>)decoder old:(MainNextcloudNewsVersion *)old __attribute__((swift_name("patch(decoder:old:)")));
- (void)serializeEncoder:(id<MainKotlinx_serialization_runtime_nativeEncoder>)encoder obj:(MainNextcloudNewsVersion *)obj __attribute__((swift_name("serialize(encoder:obj:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ActualKt")))
@interface MainActualKt : KotlinBase
+ (void)setupDatabase __attribute__((swift_name("setupDatabase()")));
@property (class) id<MainSqldelight_runtimeSqlDriver> _Nullable sqlDriver __attribute__((swift_name("sqlDriver")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExtensionFunctionsKt")))
@interface MainExtensionFunctionsKt : KotlinBase
+ (void)asyncBlock:(id<MainKotlinSuspendFunction1>)block __attribute__((swift_name("async(block:)")));
+ (NSString *)firstImageUrl:(NSString *)receiver __attribute__((swift_name("firstImageUrl(_:)")));
+ (int64_t)toLong:(BOOL)receiver __attribute__((swift_name("toLong(_:)")));
+ (BOOL)toBoolean:(int64_t)receiver __attribute__((swift_name("toBoolean(_:)")));
+ (BOOL)isFeedCacheOutdated:(id<MainUser>)receiver __attribute__((swift_name("isFeedCacheOutdated(_:)")));
+ (BOOL)isStarredCacheOutdated:(id<MainUser>)receiver __attribute__((swift_name("isStarredCacheOutdated(_:)")));
+ (int32_t)minutes:(int32_t)receiver __attribute__((swift_name("minutes(_:)")));
+ (NSString *)snipped:(NSString *)receiver __attribute__((swift_name("snipped(_:)")));
+ (BOOL)isEmailValid:(NSString *)receiver __attribute__((swift_name("isEmailValid(_:)")));
@end;

__attribute__((swift_name("Sqldelight_runtimeQuery")))
@interface MainSqldelight_runtimeQuery : KotlinBase
- (instancetype)initWithQueries:(NSMutableArray<MainSqldelight_runtimeQuery *> *)queries mapper:(id (^)(id<MainSqldelight_runtimeSqlCursor>))mapper __attribute__((swift_name("init(queries:mapper:)"))) __attribute__((objc_designated_initializer));
- (void)addListenerListener:(id<MainSqldelight_runtimeQueryListener>)listener __attribute__((swift_name("addListener(listener:)")));
- (id<MainSqldelight_runtimeSqlCursor>)execute __attribute__((swift_name("execute()")));
- (NSArray<id> *)executeAsList __attribute__((swift_name("executeAsList()")));
- (id)executeAsOne __attribute__((swift_name("executeAsOne()")));
- (id _Nullable)executeAsOneOrNull __attribute__((swift_name("executeAsOneOrNull()")));
- (void)notifyDataChanged __attribute__((swift_name("notifyDataChanged()")));
- (void)removeListenerListener:(id<MainSqldelight_runtimeQueryListener>)listener __attribute__((swift_name("removeListener(listener:)")));
@property (readonly) id (^mapper)(id<MainSqldelight_runtimeSqlCursor>) __attribute__((swift_name("mapper")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface MainKotlinUnit : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeTransacterTransaction")))
@interface MainSqldelight_runtimeTransacterTransaction : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)afterCommitFunction:(MainKotlinUnit *(^)(void))function __attribute__((swift_name("afterCommit(function:)")));
- (void)afterRollbackFunction:(MainKotlinUnit *(^)(void))function __attribute__((swift_name("afterRollback(function:)")));
- (void)endTransactionSuccessful:(BOOL)successful __attribute__((swift_name("endTransaction(successful:)")));
- (void)rollback __attribute__((swift_name("rollback()")));
- (void)transactionBody:(MainKotlinUnit *(^)(MainSqldelight_runtimeTransacterTransaction *))body __attribute__((swift_name("transaction(body:)")));
@property (readonly) MainSqldelight_runtimeTransacterTransaction * _Nullable enclosingTransaction __attribute__((swift_name("enclosingTransaction")));
@end;

__attribute__((swift_name("Sqldelight_runtimeCloseable")))
@protocol MainSqldelight_runtimeCloseable
@required
- (void)close __attribute__((swift_name("close()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlDriver")))
@protocol MainSqldelight_runtimeSqlDriver <MainSqldelight_runtimeCloseable>
@required
- (MainSqldelight_runtimeTransacterTransaction * _Nullable)currentTransaction __attribute__((swift_name("currentTransaction()")));
- (void)executeIdentifier:(MainInt * _Nullable)identifier sql:(NSString *)sql parameters:(int32_t)parameters binders:(MainKotlinUnit *(^ _Nullable)(id<MainSqldelight_runtimeSqlPreparedStatement>))binders __attribute__((swift_name("execute(identifier:sql:parameters:binders:)")));
- (id<MainSqldelight_runtimeSqlCursor>)executeQueryIdentifier:(MainInt * _Nullable)identifier sql:(NSString *)sql parameters:(int32_t)parameters binders:(MainKotlinUnit *(^ _Nullable)(id<MainSqldelight_runtimeSqlPreparedStatement>))binders __attribute__((swift_name("executeQuery(identifier:sql:parameters:binders:)")));
- (MainSqldelight_runtimeTransacterTransaction *)doNewTransaction __attribute__((swift_name("doNewTransaction()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlDriverSchema")))
@protocol MainSqldelight_runtimeSqlDriverSchema
@required
- (void)createDriver:(id<MainSqldelight_runtimeSqlDriver>)driver __attribute__((swift_name("create(driver:)")));
- (void)migrateDriver:(id<MainSqldelight_runtimeSqlDriver>)driver oldVersion:(int32_t)oldVersion newVersion:(int32_t)newVersion __attribute__((swift_name("migrate(driver:oldVersion:newVersion:)")));
@property (readonly) int32_t version __attribute__((swift_name("version")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface MainKotlinArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size init:(id _Nullable (^)(MainInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (id _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<MainKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(id _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeEncoder")))
@protocol MainKotlinx_serialization_runtime_nativeEncoder
@required
- (id<MainKotlinx_serialization_runtime_nativeCompositeEncoder>)beginCollectionDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc collectionSize:(int32_t)collectionSize typeParams:(MainKotlinArray *)typeParams __attribute__((swift_name("beginCollection(desc:collectionSize:typeParams:)")));
- (id<MainKotlinx_serialization_runtime_nativeCompositeEncoder>)beginStructureDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc typeParams:(MainKotlinArray *)typeParams __attribute__((swift_name("beginStructure(desc:typeParams:)")));
- (void)encodeBooleanValue:(BOOL)value __attribute__((swift_name("encodeBoolean(value:)")));
- (void)encodeByteValue:(int8_t)value __attribute__((swift_name("encodeByte(value:)")));
- (void)encodeCharValue:(unichar)value __attribute__((swift_name("encodeChar(value:)")));
- (void)encodeDoubleValue:(double)value __attribute__((swift_name("encodeDouble(value:)")));
- (void)encodeEnumEnumDescription:(MainKotlinx_serialization_runtime_nativeEnumDescriptor *)enumDescription ordinal:(int32_t)ordinal __attribute__((swift_name("encodeEnum(enumDescription:ordinal:)")));
- (void)encodeFloatValue:(float)value __attribute__((swift_name("encodeFloat(value:)")));
- (void)encodeIntValue:(int32_t)value __attribute__((swift_name("encodeInt(value:)")));
- (void)encodeLongValue:(int64_t)value __attribute__((swift_name("encodeLong(value:)")));
- (void)encodeNotNullMark __attribute__((swift_name("encodeNotNullMark()")));
- (void)encodeNull __attribute__((swift_name("encodeNull()")));
- (void)encodeNullableSerializableValueSerializer:(id<MainKotlinx_serialization_runtime_nativeSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeNullableSerializableValue(serializer:value:)")));
- (void)encodeSerializableValueSerializer:(id<MainKotlinx_serialization_runtime_nativeSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeSerializableValue(serializer:value:)")));
- (void)encodeShortValue:(int16_t)value __attribute__((swift_name("encodeShort(value:)")));
- (void)encodeStringValue:(NSString *)value __attribute__((swift_name("encodeString(value:)")));
- (void)encodeUnit __attribute__((swift_name("encodeUnit()")));
@property (readonly) id<MainKotlinx_serialization_runtime_nativeSerialModule> context __attribute__((swift_name("context")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeSerialDescriptor")))
@protocol MainKotlinx_serialization_runtime_nativeSerialDescriptor
@required
- (NSArray<id<MainKotlinAnnotation>> *)getElementAnnotationsIndex:(int32_t)index __attribute__((swift_name("getElementAnnotations(index:)")));
- (id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)getElementDescriptorIndex:(int32_t)index __attribute__((swift_name("getElementDescriptor(index:)")));
- (int32_t)getElementIndexName:(NSString *)name __attribute__((swift_name("getElementIndex(name:)")));
- (NSString *)getElementNameIndex:(int32_t)index __attribute__((swift_name("getElementName(index:)")));
- (NSArray<id<MainKotlinAnnotation>> *)getEntityAnnotations __attribute__((swift_name("getEntityAnnotations()")));
- (BOOL)isElementOptionalIndex:(int32_t)index __attribute__((swift_name("isElementOptional(index:)")));
@property (readonly) int32_t elementsCount __attribute__((swift_name("elementsCount")));
@property (readonly) BOOL isNullable __attribute__((swift_name("isNullable")));
@property (readonly) MainKotlinx_serialization_runtime_nativeSerialKind *kind __attribute__((swift_name("kind")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeDecoder")))
@protocol MainKotlinx_serialization_runtime_nativeDecoder
@required
- (id<MainKotlinx_serialization_runtime_nativeCompositeDecoder>)beginStructureDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc typeParams:(MainKotlinArray *)typeParams __attribute__((swift_name("beginStructure(desc:typeParams:)")));
- (BOOL)decodeBoolean __attribute__((swift_name("decodeBoolean()")));
- (int8_t)decodeByte __attribute__((swift_name("decodeByte()")));
- (unichar)decodeChar __attribute__((swift_name("decodeChar()")));
- (double)decodeDouble __attribute__((swift_name("decodeDouble()")));
- (int32_t)decodeEnumEnumDescription:(MainKotlinx_serialization_runtime_nativeEnumDescriptor *)enumDescription __attribute__((swift_name("decodeEnum(enumDescription:)")));
- (float)decodeFloat __attribute__((swift_name("decodeFloat()")));
- (int32_t)decodeInt __attribute__((swift_name("decodeInt()")));
- (int64_t)decodeLong __attribute__((swift_name("decodeLong()")));
- (BOOL)decodeNotNullMark __attribute__((swift_name("decodeNotNullMark()")));
- (MainKotlinNothing * _Nullable)decodeNull __attribute__((swift_name("decodeNull()")));
- (id _Nullable)decodeNullableSerializableValueDeserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer __attribute__((swift_name("decodeNullableSerializableValue(deserializer:)")));
- (id _Nullable)decodeSerializableValueDeserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer __attribute__((swift_name("decodeSerializableValue(deserializer:)")));
- (int16_t)decodeShort __attribute__((swift_name("decodeShort()")));
- (NSString *)decodeString __attribute__((swift_name("decodeString()")));
- (void)decodeUnit __attribute__((swift_name("decodeUnit()")));
- (id _Nullable)updateNullableSerializableValueDeserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer old:(id _Nullable)old __attribute__((swift_name("updateNullableSerializableValue(deserializer:old:)")));
- (id _Nullable)updateSerializableValueDeserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer old:(id _Nullable)old __attribute__((swift_name("updateSerializableValue(deserializer:old:)")));
@property (readonly) id<MainKotlinx_serialization_runtime_nativeSerialModule> context __attribute__((swift_name("context")));
@property (readonly) MainKotlinx_serialization_runtime_nativeUpdateMode *updateMode __attribute__((swift_name("updateMode")));
@end;

__attribute__((swift_name("KotlinSuspendFunction")))
@protocol MainKotlinSuspendFunction
@required
@end;

__attribute__((swift_name("KotlinSuspendFunction1")))
@protocol MainKotlinSuspendFunction1 <MainKotlinSuspendFunction>
@required
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlCursor")))
@protocol MainSqldelight_runtimeSqlCursor <MainSqldelight_runtimeCloseable>
@required
- (MainKotlinByteArray * _Nullable)getBytesIndex:(int32_t)index __attribute__((swift_name("getBytes(index:)")));
- (MainDouble * _Nullable)getDoubleIndex:(int32_t)index __attribute__((swift_name("getDouble(index:)")));
- (MainLong * _Nullable)getLongIndex:(int32_t)index __attribute__((swift_name("getLong(index:)")));
- (NSString * _Nullable)getStringIndex:(int32_t)index __attribute__((swift_name("getString(index:)")));
- (BOOL)next __attribute__((swift_name("next()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeQueryListener")))
@protocol MainSqldelight_runtimeQueryListener
@required
- (void)queryResultsChanged __attribute__((swift_name("queryResultsChanged()")));
@end;

__attribute__((swift_name("Sqldelight_runtimeSqlPreparedStatement")))
@protocol MainSqldelight_runtimeSqlPreparedStatement
@required
- (void)bindBytesIndex:(int32_t)index value:(MainKotlinByteArray * _Nullable)value __attribute__((swift_name("bindBytes(index:value:)")));
- (void)bindDoubleIndex:(int32_t)index value:(MainDouble * _Nullable)value __attribute__((swift_name("bindDouble(index:value:)")));
- (void)bindLongIndex:(int32_t)index value:(MainLong * _Nullable)value __attribute__((swift_name("bindLong(index:value:)")));
- (void)bindStringIndex:(int32_t)index value:(NSString * _Nullable)value __attribute__((swift_name("bindString(index:value:)")));
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol MainKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next_ __attribute__((swift_name("next_()")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeCompositeEncoder")))
@protocol MainKotlinx_serialization_runtime_nativeCompositeEncoder
@required
- (void)encodeBooleanElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(BOOL)value __attribute__((swift_name("encodeBooleanElement(desc:index:value:)")));
- (void)encodeByteElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(int8_t)value __attribute__((swift_name("encodeByteElement(desc:index:value:)")));
- (void)encodeCharElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(unichar)value __attribute__((swift_name("encodeCharElement(desc:index:value:)")));
- (void)encodeDoubleElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(double)value __attribute__((swift_name("encodeDoubleElement(desc:index:value:)")));
- (void)encodeFloatElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(float)value __attribute__((swift_name("encodeFloatElement(desc:index:value:)")));
- (void)encodeIntElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(int32_t)value __attribute__((swift_name("encodeIntElement(desc:index:value:)")));
- (void)encodeLongElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(int64_t)value __attribute__((swift_name("encodeLongElement(desc:index:value:)")));
- (void)encodeNonSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(id)value __attribute__((swift_name("encodeNonSerializableElement(desc:index:value:)")));
- (void)encodeNullableSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index serializer:(id<MainKotlinx_serialization_runtime_nativeSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeNullableSerializableElement(desc:index:serializer:value:)")));
- (void)encodeSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index serializer:(id<MainKotlinx_serialization_runtime_nativeSerializationStrategy>)serializer value:(id _Nullable)value __attribute__((swift_name("encodeSerializableElement(desc:index:serializer:value:)")));
- (void)encodeShortElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(int16_t)value __attribute__((swift_name("encodeShortElement(desc:index:value:)")));
- (void)encodeStringElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index value:(NSString *)value __attribute__((swift_name("encodeStringElement(desc:index:value:)")));
- (void)encodeUnitElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("encodeUnitElement(desc:index:)")));
- (void)endStructureDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc __attribute__((swift_name("endStructure(desc:)")));
- (BOOL)shouldEncodeElementDefaultDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("shouldEncodeElementDefault(desc:index:)")));
@property (readonly) id<MainKotlinx_serialization_runtime_nativeSerialModule> context __attribute__((swift_name("context")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeSerialClassDescImpl")))
@interface MainKotlinx_serialization_runtime_nativeSerialClassDescImpl : KotlinBase <MainKotlinx_serialization_runtime_nativeSerialDescriptor>
- (instancetype)initWithName:(NSString *)name generatedSerializer:(id<MainKotlinx_serialization_runtime_nativeGeneratedSerializer> _Nullable)generatedSerializer __attribute__((swift_name("init(name:generatedSerializer:)"))) __attribute__((objc_designated_initializer));
- (void)addElementName:(NSString *)name isOptional:(BOOL)isOptional __attribute__((swift_name("addElement(name:isOptional:)")));
- (void)pushAnnotationA:(id<MainKotlinAnnotation>)a __attribute__((swift_name("pushAnnotation(a:)")));
- (void)pushClassAnnotationA:(id<MainKotlinAnnotation>)a __attribute__((swift_name("pushClassAnnotation(a:)")));
- (void)pushDescriptorDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc __attribute__((swift_name("pushDescriptor(desc:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_serialization_runtime_nativeEnumDescriptor")))
@interface MainKotlinx_serialization_runtime_nativeEnumDescriptor : MainKotlinx_serialization_runtime_nativeSerialClassDescImpl
- (instancetype)initWithName:(NSString *)name choices:(MainKotlinArray *)choices __attribute__((swift_name("init(name:choices:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithName:(NSString *)name generatedSerializer:(id<MainKotlinx_serialization_runtime_nativeGeneratedSerializer> _Nullable)generatedSerializer __attribute__((swift_name("init(name:generatedSerializer:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeSerialModule")))
@protocol MainKotlinx_serialization_runtime_nativeSerialModule
@required
- (void)dumpToCollector:(id<MainKotlinx_serialization_runtime_nativeSerialModuleCollector>)collector __attribute__((swift_name("dumpTo(collector:)")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer> _Nullable)getContextualKclass:(id<MainKotlinKClass>)kclass __attribute__((swift_name("getContextual(kclass:)")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer> _Nullable)getPolymorphicBaseClass:(id<MainKotlinKClass>)baseClass value:(id)value __attribute__((swift_name("getPolymorphic(baseClass:value:)")));
- (id<MainKotlinx_serialization_runtime_nativeKSerializer> _Nullable)getPolymorphicBaseClass:(id<MainKotlinKClass>)baseClass serializedClassName:(NSString *)serializedClassName __attribute__((swift_name("getPolymorphic(baseClass:serializedClassName:)")));
@end;

__attribute__((swift_name("KotlinAnnotation")))
@protocol MainKotlinAnnotation
@required
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeSerialKind")))
@interface MainKotlinx_serialization_runtime_nativeSerialKind : KotlinBase
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeCompositeDecoder")))
@protocol MainKotlinx_serialization_runtime_nativeCompositeDecoder
@required
- (BOOL)decodeBooleanElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeBooleanElement(desc:index:)")));
- (int8_t)decodeByteElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeByteElement(desc:index:)")));
- (unichar)decodeCharElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeCharElement(desc:index:)")));
- (int32_t)decodeCollectionSizeDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc __attribute__((swift_name("decodeCollectionSize(desc:)")));
- (double)decodeDoubleElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeDoubleElement(desc:index:)")));
- (int32_t)decodeElementIndexDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc __attribute__((swift_name("decodeElementIndex(desc:)")));
- (float)decodeFloatElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeFloatElement(desc:index:)")));
- (int32_t)decodeIntElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeIntElement(desc:index:)")));
- (int64_t)decodeLongElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeLongElement(desc:index:)")));
- (id _Nullable)decodeNullableSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index deserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer __attribute__((swift_name("decodeNullableSerializableElement(desc:index:deserializer:)")));
- (id _Nullable)decodeSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index deserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer __attribute__((swift_name("decodeSerializableElement(desc:index:deserializer:)")));
- (int16_t)decodeShortElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeShortElement(desc:index:)")));
- (NSString *)decodeStringElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeStringElement(desc:index:)")));
- (void)decodeUnitElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index __attribute__((swift_name("decodeUnitElement(desc:index:)")));
- (void)endStructureDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc __attribute__((swift_name("endStructure(desc:)")));
- (id _Nullable)updateNullableSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index deserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer old:(id _Nullable)old __attribute__((swift_name("updateNullableSerializableElement(desc:index:deserializer:old:)")));
- (id _Nullable)updateSerializableElementDesc:(id<MainKotlinx_serialization_runtime_nativeSerialDescriptor>)desc index:(int32_t)index deserializer:(id<MainKotlinx_serialization_runtime_nativeDeserializationStrategy>)deserializer old:(id _Nullable)old __attribute__((swift_name("updateSerializableElement(desc:index:deserializer:old:)")));
@property (readonly) id<MainKotlinx_serialization_runtime_nativeSerialModule> context __attribute__((swift_name("context")));
@property (readonly) MainKotlinx_serialization_runtime_nativeUpdateMode *updateMode __attribute__((swift_name("updateMode")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinNothing")))
@interface MainKotlinNothing : KotlinBase
@end;

__attribute__((swift_name("KotlinComparable")))
@protocol MainKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((swift_name("KotlinEnum")))
@interface MainKotlinEnum : KotlinBase <MainKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
- (int32_t)compareToOther:(MainKotlinEnum *)other __attribute__((swift_name("compareTo(other:)")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Kotlinx_serialization_runtime_nativeUpdateMode")))
@interface MainKotlinx_serialization_runtime_nativeUpdateMode : MainKotlinEnum
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
@property (class, readonly) MainKotlinx_serialization_runtime_nativeUpdateMode *banned __attribute__((swift_name("banned")));
@property (class, readonly) MainKotlinx_serialization_runtime_nativeUpdateMode *overwrite __attribute__((swift_name("overwrite")));
@property (class, readonly) MainKotlinx_serialization_runtime_nativeUpdateMode *update __attribute__((swift_name("update")));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (int32_t)compareToOther:(MainKotlinx_serialization_runtime_nativeUpdateMode *)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinByteArray")))
@interface MainKotlinByteArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size __attribute__((swift_name("init(size:)")));
+ (instancetype)arrayWithSize:(int32_t)size init:(MainByte *(^)(MainInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (int8_t)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (MainKotlinByteIterator *)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(int8_t)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("Kotlinx_serialization_runtime_nativeSerialModuleCollector")))
@protocol MainKotlinx_serialization_runtime_nativeSerialModuleCollector
@required
- (void)contextualKClass:(id<MainKotlinKClass>)kClass serializer:(id<MainKotlinx_serialization_runtime_nativeKSerializer>)serializer __attribute__((swift_name("contextual(kClass:serializer:)")));
- (void)polymorphicBaseClass:(id<MainKotlinKClass>)baseClass actualClass:(id<MainKotlinKClass>)actualClass actualSerializer:(id<MainKotlinx_serialization_runtime_nativeKSerializer>)actualSerializer __attribute__((swift_name("polymorphic(baseClass:actualClass:actualSerializer:)")));
@end;

__attribute__((swift_name("KotlinKDeclarationContainer")))
@protocol MainKotlinKDeclarationContainer
@required
@end;

__attribute__((swift_name("KotlinKAnnotatedElement")))
@protocol MainKotlinKAnnotatedElement
@required
@end;

__attribute__((swift_name("KotlinKClassifier")))
@protocol MainKotlinKClassifier
@required
@end;

__attribute__((swift_name("KotlinKClass")))
@protocol MainKotlinKClass <MainKotlinKDeclarationContainer, MainKotlinKAnnotatedElement, MainKotlinKClassifier>
@required
- (BOOL)isInstanceValue:(id _Nullable)value __attribute__((swift_name("isInstance(value:)")));
@property (readonly) NSString * _Nullable qualifiedName __attribute__((swift_name("qualifiedName")));
@property (readonly) NSString * _Nullable simpleName __attribute__((swift_name("simpleName")));
@end;

__attribute__((swift_name("KotlinByteIterator")))
@interface MainKotlinByteIterator : KotlinBase <MainKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (MainByte *)next_ __attribute__((swift_name("next_()")));
- (int8_t)nextByte __attribute__((swift_name("nextByte()")));
@end;

NS_ASSUME_NONNULL_END
