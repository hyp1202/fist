//package aiguquan.dingding.exception;
//
//import aiguquan.dingding.model.ServiceResult;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class GlobalExceptionResolver {
//
//    /**
//     * 处理所有不可知异常
//     *
//     * @param e 异常
//     * @return json结果
//     */
////    @ExceptionHandler(Exception.class)
////    public ServiceResult handleException(Exception e) {
////        // 打印异常堆栈信息
////        log.error(e.getMessage(), e);
////        return ServiceResult.getFailureResult(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
////    }
//
//    /**
//     * 处理api调用异常
//     *
//     * @param e 异常
//     * @return json结果
//     */
////    @ExceptionHandler(InvokeDingTalkException.class)
//    public ServiceResult handleInvokeDingTalkException(InvokeDingTalkException e) {
//        // 打印异常堆栈信息
//        log.error(e.getMessage(), e);
//        return ServiceResult.getFailureResult(e.getErrCode(), e.getErrMsg());
//    }
//
//}
