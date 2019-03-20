package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import kotlinx.android.synthetic.main.item_put_recode.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class PutRecodeAdapter:BaseQuickAdapter<Any,BaseViewHolder>(R.layout.item_put_recode) {
    override fun convert(helper: BaseViewHolder?, item: Any?) {
        helper?.itemView?.apply {
            tv_time.text="2018-01-01 12:12\n2018-01-01 12:13"
            tv_num.text="02"
            tv_area.text="新都区\n新都区1"
        }
    }
}