package com.nntsl.feature.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.nntsl.R
import com.nntsl.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private val binding: FragmentUserDetailsBinding by viewBinding(FragmentUserDetailsBinding::bind)

    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.user.let { user ->
            with(binding) {
                menuBack.setOnClickListener {
                    findNavController().popBackStack()
                }
                userNameTextView.text = user.name
                user.avatar?.let {
                    userAvatarImageView.load(user.avatar) {
                        transformations(
                            CircleCropTransformation()
                        )
                        build()
                    }
                } ?: userAvatarImageView.setImageDrawable(
                    AppCompatResources.getDrawable(requireContext(), R.drawable.ic_default_avatar)
                )
                userSourceTextView.text = user.source
            }
        }
    }
}
